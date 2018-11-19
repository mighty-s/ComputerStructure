package LRU;

/**
 * @author sight
 * @since 2018-11-19
 */
public class Block
{
    // -----------------------------   Field   -------------------------------
    private int unUsedCount;                // 사용을 얼마나 안했는지 체크하는 필드
    private String blockNumber;             // 블록 번호

    // ---------------------------- Constructors -------------------------------
    public Block(String blockNumber)
    {
        this.unUsedCount = 0;
        this.blockNumber = blockNumber;
    }

    // --------------------------- Public Operations  ---------------------------
    // 1 추가
    public void addCount()
    {
        this.unUsedCount++;
    }

    /**
     *  미사용 주기를 0으로 재설정
     */
    public void resetCount()
    {
        unUsedCount = 0;
    }
    /**
     * 해당 블록이 같은 블록인지 판별 (블록 number 로 구분)
     * @param obj   비교하고자 하는 블록
     * @return      같은 블록인지 여부
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Block)     // 같은 인스턴스인지 체크
            return this.blockNumber.equals(((Block)obj).blockNumber); // 같은 blockNumber 값을 가지고 있는지 체크
        else
            return false;
    }
    // --------------------------- Private Operations ---------------------------


    // ---------------------------  Getters & Setters ---------------------------
    public int getUnUsedCount() {
        return unUsedCount;
    }

    public void setUnUsedCount(int unUsedCount) {
        this.unUsedCount = unUsedCount;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }
}
