package LRU;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author sight
 * @since 2018-11-19
 *
 * 캐시 블록 목록
 */
public class Cache
{
    // -----------------------------   Field   -------------------------------
    private final Block[] cache;      // 캐시 라인
    private int hitCount;             // 캐시 Hit  횟수
    private int missCount;            // 캐시 Miss 횟수

    // ---------------------------- Constructors -------------------------------
    public Cache(int blockSize)
    {
        this.cache = new Block[blockSize];
    }

    // --------------------------- Public Operations ------------------------
    /**
     * 새로운 블록이 들어왔을 때 처리
     * @param block 주 메모리 블록
     */
    public void doProcess(Block block)
    {
        doCycle();

        if(isContain(block))    // 캐시에 해당 블록이 존재 -> HIT
            hit(block);
        else                    // 캐시에 해당 블록 없음
            if(isFull())        //    1. 캐시에 여유 공간 없음 -> 교체 정책 실행 ( LRU )
                replace(block);
            else                //    2. 캐시에 여유 공간 있음 -> 그냥 적재
                insert(block);
    }// end doProcess()

    /**
     * 캐시 내용물 출력
     */
    public void printCache()
    {
        System.out.println("-----------------------");

        for(Block block : cache)
        {
            System.out.printf(
                    "|          %s          |\n" +
                    "+---------------------+\n" , block.getBlockNumber()
            );
        }
    }

    public void printHitRatio(int blockSize)
    {
        System.out.printf("캐시의 적중률 : %f %%", getHitRatio(blockSize));
    }

    /**
     * 적중률을 구하는 함수
     * @param inputSize 입력받는 값의 개수
     * @return 적중률
     */
    public double getHitRatio(int inputSize)
    {
        return ((double)hitCount/(double)inputSize) * 100;
    }

    // --------------------------- Private Operations ------------------------

    /**
     * 캐시에서 해당 블록을 hit 처리하는 함수
     * @param block 찾을 블록
     */
    private void hit(Block block)
    {
        hitCount++;
        find(block).resetCount();   // 해당 블록의 미사용 주기를 0으로 초기화
        // System.out.println("페이지 히트 발생 : " + block.getBlockNumber());
    }

    /**
     * LRU 기반의 캐시 교체 정책
     * @param block 적재할 블록
     */
    private void replace(Block block)
    {
        missCount++;
        int line = getMinUnusedLine(); // 교체될 캐시의 인덱스
        cache[line] = block;
    }

    /**
     * 캐시에 새로운 블록 적재 ( 이때 캐시는 여유 공간이 있어야만 함 )
     * @param block 적재할 블록
     */
    private void insert(Block block)
    {
        missCount++;
        for(int i = 0 ; i < cache.length ; i++)
        {
            if(cache[i] == null)
            {
                cache[i] = block;
                break;
            }
        }
    }// end insert()

    /**
     * 캐시 블록에 있는 블록들 전부
     * 미사용 주기 1씩 증가
     */
    private void doCycle()
    {
        Arrays.stream(cache)                // cache 배열 가져오기 ( 사본 )
              .filter(Objects::nonNull)     // stream 에서 null 제거
              .forEach(Block::addCount);    // null 을 제회한 블록들 미사용 주기 증가
    }

    /**
     * 제일 안쓰인 캐시의 인덱스를 리턴
     * @return LRU 교체가 적용될 캐시 라인 ( 인덱스 )
     */
    private int getMinUnusedLine()
    {
        int minUsedCount = 0;
        int minUsedIndex = 0;
        for(int i = 0 ; i < cache.length ; i++)
        {
            if( minUsedCount < cache[i].getUnUsedCount() )
            {
                minUsedIndex = i;
                minUsedCount = cache[i].getUnUsedCount();
            }
        }
        return minUsedIndex;
    }
    /**
     * 찾고자 하는 주 메모리 블록이
     * 캐시에 있는 경우 판별
     * @param block 접근하고자 하는 블록
     * @return  접근하고자 하는 블록이 캐시에 있는지 여부
     */
    private boolean isContain(Block block)
    {
        return Arrays.stream(cache)                                 // cache 배열 가져오기 ( 사본 )
                     .filter(Objects::nonNull)                      // null 이 아닌 블록만 선택
                     .anyMatch(cBlock -> cBlock.equals(block));     //   ㄴ> 그중 하나라도 일치하면 참
    }

    /**
     * 캐시에 빈공간이 있는지 판별하는 함수
     * @return  다 차있으면 true, 공간 있으면 false
     */
    private boolean isFull()
    {
        return Arrays.stream(cache)
                     .allMatch(Objects::nonNull);   // cache 배열이 모두 null 일때만 true 반환
    }

    /**
     * 캐시에서 블록을 찾아서 해당 레퍼런스 리턴ㄴ
     * @param block 캐시에서 찾으려는 블록
     * @return  캐시에서 찾은 블록 ( 없으면 null 리턴 )
     */
    private Block find(Block block)
    {
        for(int i = 0 ; i < cache.length ; i++ )
        {
            if(cache[i].equals(block))
                return cache[i];
        }
        return null;
    }
}
