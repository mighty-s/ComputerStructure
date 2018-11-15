package strategy.LRU;

/**
 * @author sight
 * @since 2018-11-15
 */
public class Block implements Comparable<Block>
{
    // -----------------------------   Field   -------------------------------
    private int unUsedCount;          // 사용을 얼마나 안했는지 체크하는 필드
    private String blockNumber;       // 블록 번호

    // ---------------------------- Constructors  ----------------------------
    public Block()
    {
        this.unUsedCount = 99;
        this.blockNumber = "99";
    }

    public Block(String blockNumber)
    {
        this.unUsedCount = 0;
        this.blockNumber = blockNumber;
    }

    // --------------------------- Public Operations ------------------------
    public boolean isSame(String blockNumber)
    {
        return blockNumber.equals(this.blockNumber);
    }

    @Override
    public int compareTo(Block target)
    {
        if(this.unUsedCount > target.unUsedCount)
            return -1;
        else if(this.unUsedCount < target.unUsedCount)
            return 1;
        else
            return 0;
    }

    public void resetCount()
    {
        this.unUsedCount = 0;
    }

    public void countUsage()
    {
        this.unUsedCount++;
    }

    // --------------------- Private Operations  -------------------------------

    // ---------------------  Getters & Setters  -------------------------------
    public String getBlockNumber() {
        return blockNumber;
    }

    public int getUnUsedCount() {
        return unUsedCount;
    }
}
