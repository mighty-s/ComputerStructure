package strategy.LRU;

import strategy.CacheStrategy;
import strategy.ResultSet;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author sight
 * @since 2018-11-15
 *
 * LRU 알고리즘
 */
public class LRU implements CacheStrategy
{
    // --------------------------      Fields        --------------------------
    private int hitCount;                           // hit  한 횟수
    private int missCount;                          // miss 한 횟수
    private static final int LRU_LINE_SIZE = 3;     // LRU 캐시 라인 길이

    // ---------------------------    Constructors   --------------------------

    public LRU()
    {
        this.hitCount  = 0;
        this.missCount = 0;
    }

    // --------------------------- Public Operations --------------------------

    /**
     * 이곳에서 로직이 시작됩니다.
     *
     * @param blocks 프로세스 블록
     * @return 결과 값이 담겨있는 ResultSet 인스턴스
     */
    @Override
    public ResultSet doProcess(String[] blocks)
    {
        final Queue<Block> pq = new PriorityQueue<>(LRU_LINE_SIZE);

        for(String blockNum : blocks)
        {
            LRU_process(pq, blockNum);
//            pq.forEach(i->System.out.println(i.getBlockNumber() + " | " + i.getUnUsedCount()));
//            System.out.println("");
        }
//        pq.forEach(i->System.out.print(i.getBlockNumber() + " "));
//        System.out.printf("\t 캐시 적중률 : %f", ((double)hitCount/(double)blocks.length)*100);

        String[] remain = pq.stream()
                            .map(Block::getBlockNumber)
                            .toArray(String[]::new);

        return new ResultSet(remain, hitCount, missCount, blocks.length);   // 결과값 리턴
    }

    // ---------------------------- Private Operations --------------------------

    /**
     * LRU 에서 블록 하나에 대해 작업을 싱행하는 함수
     * @param pq           우선순위 큐 (캐시 블록)
     * @param blockNum     해당 블록 번호
     */
    private void LRU_process(Queue<Block> pq, String blockNum)
    {
        if( hasBlock(pq , blockNum) )                       // 1) 큐에 해당 블록이 있을 경우 -> Hit 한 경우
        {
//            System.out.printf("hit 발생 %s\n", blockNum);
            Block block  = findBlock(pq, blockNum);         //      hit 된 블록 검색
            pq.forEach(Block::countUsage);                  //      캐시 사용 주기 증가
            block.resetCount();                             //      블록 0으로 초기화
            hitCount++;                                     //      hit 카운터 증가

        }else{                                              //  2) 큐에 해당 블록이 없을 경우 -> Hit 실패 ( Miss )
            pq.forEach(Block::countUsage);                  //      캐시 사용 주기 증가
            LRU_insert(pq, blockNum);                       //      우선순위 큐(캐시블록)에 새로운 블록을 적재
            missCount++;                                    //      miss 카운터 증가
        }
    }

    /**
     * LRU 알고리즘에서 캐시 블록을 교체 및 추가 할때
     * 사용하는 함수
     *
     * @param pq           우선순위 큐 (캐시 블록)
     * @param blockNum     해당 블록 번호
     */
    private void LRU_insert(Queue<Block> pq, String blockNum)
    {
        if( pq.size() >= LRU_LINE_SIZE )                    // 1) 캐시 블록 수보다 클때 -> 교체 알고리즘 사용 ( LRU )
        {
//            System.out.printf("Miss 발생 %s\n",blockNum);
            pq.offer(new Block());                          //              우선순위 큐 트릭....
            pq.poll();                                      //              우선순위 큐 트릭....
            pq.offer(new Block(blockNum));                  //      새로운 블록 추가
            pq.poll();                                      //      우선순위 큐에서 제일 앞에있는거 하나 제거 ( 제일 오래동안 안쓰인 블록 )
        }else{                                              // 2) 캐시블록 수보다 잘을 때 -> 페이지 폴트 없음
            pq.offer(new Block(blockNum));                  //     ㄴ> 그냥 새로운 블록 추가
        }
    }

    /**
     * 캐시블록에 해당 번호를 가진 블록이
     * 존재하는지의 여부
     *
     * @param pq            우선순위 큐 ( 캐시블록 )
     * @param blockNum      블록 번호
     * @return              해당 블록이 캐시블록에 존재하는지 여부
     */
    private boolean hasBlock(Queue<Block> pq, String blockNum)
    {
        return pq.stream()
                 .anyMatch(block-> blockNum.equals(block.getBlockNumber()));
    }

    /**
     * 캐시 블록에 blockNum 인  데이터가 있는지 확인하는 함수,
     * 없을경우 null 을 반환한다.
     *
     * @param pq           우선순위 큐 (캐시 블록)
     * @param blockNum     해당 블록 번호
     * @return             해당 블록 번호와 일치하는 캐시블록
     */
    private Block findBlock(Queue<Block> pq, String blockNum)
    {
        return pq.stream()
                 .filter(block -> blockNum.equals(block.getBlockNumber()))
                 .findFirst()
                 .orElse(null);
    }
}
