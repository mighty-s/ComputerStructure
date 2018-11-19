/**
 * @author sight
 * @since 2018-11-15
 *
 * 결과값을 모아둔 클래스
 */
public class ResultSet
{
    // -----------------------  Fields  -----------------------------
    private int blockLength;            // 블록 길이
    private int hitCount;               // Hit 횟수
    private int missCount;              // 캐시 miss 횟수
    private String[] blocks;            // 캐시 블록에 남은 블록들

    // ---------------------- Constructor ---------------------------
    public ResultSet(String[] blocks, int hitCount, int missCount, int blockLength)
    {
        this.blocks      = blocks;
        this.hitCount    = hitCount;
        this.missCount   = missCount;
        this.blockLength = blockLength;
    }

    // ---------------------  Public Operations ----------------------

    /**
     * 적중률을 구하는 함수
     * @return 적중률
     */
    public double getHitRatio()
    {
        return ((double)this.hitCount/(double)this.blockLength) * 100;
    }

    /**
     * 캐시블록에 있는 블록 번호들 표시   [ 1 | 2 | 3 ] 형식으로 리터
     * @return 블록 번호들
     */
    public String getCacheBlockRemain()
    {
        return "[ " + String.join(" | ", this.blocks) +" ]";
    }

    /**
     * Object 클래스의 toString() 오버라이딩
     * @return 현재 ResultSet 의 캐시 블록 내역, 적중률 String 으로 리턴
     */
    @Override
    public String toString() {
        return String.format(
                "%s | %4.2f %%", getCacheBlockRemain(), getHitRatio()
        );
    }

    // --------------------- Private Operations ----------------------



}