package strategy.LRU;

import strategy.CacheStrategy;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author sight
 * @since 2018-11-15
 */
public class LRU implements CacheStrategy
{
    private int hitCount;     // hit 한 횟수

    public LRU()
    {
        hitCount = 0;
    }

    // --------------------------- Public Operations --------------------------
    @Override
    public String doProcess(String[] blocks)
    {
        final Queue<Block> pq = new PriorityQueue<>(3);

        for(String blockNum : blocks)
        {
            LRU_process(pq, blockNum);

            pq.forEach(i->System.out.println(i.getBlockNumber() + " | " + i.getUnUsedCount()));
            System.out.println("");
        }

        pq.forEach(i->System.out.print(i.getBlockNumber() + " "));
        return null;
    }

    // ---------------------------- Private Operations --------------------------
    private void LRU_process(Queue<Block> pq, String blockNum)
    {
        if( hasBlock(pq , blockNum) )           // 큐에 해당 블록이 있을 경우 -> Hit 한 경우
        {
            System.out.printf("hit 발생 %s\n", blockNum);
            Block block  = findElement(pq, blockNum);
            pq.forEach(Block::countUsage);
            block.resetCount();
            hitCount++;
        }else{                                  // 큐에 해당 블록이 없을 경우 -> Hit 실패
            pq.forEach(Block::countUsage);
            LRU_insert(pq, blockNum);
        }
    }

    private void LRU_insert(Queue<Block> pq, String blockNum)
    {
        if( pq.size() >= 3 )               // 1) 캐시 블록 수보다 클때 -> 페이지 폴트 발생
        {
            System.out.printf("페이지 폴트 발생 %s\n",blockNum);
            pq.offer(new Block());
            pq.poll();
            pq.offer(new Block(blockNum)); //   새로운 블록 추가
            pq.poll();                     //   우선순위 큐에서 제일 앞에있는거 하나 제거 ( 제일 오래동안 안쓰인 블록 )
        }else{                             // 2) 캐시블록 수보다 잘을 때 -> 페이지 폴트 없음
            pq.offer(new Block(blockNum));   //     ㄴ> 그냥 새로운 블록 추가
        }
    }

    private boolean hasBlock(Queue<Block> pq, String blockNum)
    {
        return pq.stream()
                 .anyMatch(block-> blockNum.equals(block.getBlockNumber()));
    }

    private Block findElement(Queue<Block> pq, String blockNum)
    {
        return pq.stream()
                 .filter(block -> blockNum.equals(block.getBlockNumber()))
                 .findFirst()
                 .orElse(null);
    }
}
