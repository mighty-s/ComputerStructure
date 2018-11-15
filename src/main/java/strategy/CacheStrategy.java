package strategy;

/**
 * @author sight
 * @since 2018-11-13
 *
 * 가상 메모리 교체 정책,
 * 전략 패턴용 인터페이스
 */
public interface CacheStrategy
{
    String doProcess(String[] blocks);
}
