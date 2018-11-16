package strategy;

/**
 * @author sight
 * @since 2018-11-13
 *
 * 가상 메모리 교체 정책 인터페이스
 */
public interface CacheStrategy
{
    ResultSet doProcess(String[] blocks);
}
