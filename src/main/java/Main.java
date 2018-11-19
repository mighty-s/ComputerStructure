import LRU.Block;
import LRU.Cache;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author sight
 * @since 2018-11-19
 *
 * 다시 짜즈아... ㅜㅜ
 */
public class Main
{

    // Entering Point
    public static void main(String[] args) throws IOException
    {
        String[] blocks = readFile("src/main/resources/input2.txt");

        Cache cache = new Cache(3);

        for(String block : blocks)
        {
            cache.doProcess(new Block(block));
        }

        cache.printCache();
        cache.printHitRatio(blocks.length);
    }

    /**
     * input.txt 파일을 읽어서 String 배열을 리턴하는 함수
     * @param PATH  파일의 경로
     * @return "99"를 제외한 나머지 블록 번호가 들어있는 문자열 배열
     * @throws IOException  처리 안함
     */
    private static String[] readFile(String PATH) throws IOException
    {
        Scanner sc = new Scanner(new FileReader(PATH));
        StringBuilder sb = new StringBuilder(100);

        while(sc.hasNext())
            sb.append(sc.nextLine());

        sc.close();
        Stream<String> stream = Stream.of(sb.toString().split(" "));
        return  stream.filter(i-> !"99".equals(i))
                .toArray(String[]::new);
    }
}
