package first_try;

import first_try.strategy.CacheStrategy;
import first_try.strategy.LRU.LRU;
import first_try.strategy.ResultSet;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author sight
 * @since 2018-11-13
 */
public class Main
{
    // Enter Point
    public static void main(String[] args) throws IOException
    {
        String[] blocks = readFile("src/main/resources/input2.txt");

        printMenu(blocks);
    }

    /**
     * 메뉴를 출력하는 함수
     * @param blocks input.txt 파일을 읽고 "99"를 제외한 블록들
     */
    private static void printMenu(String[] blocks)
    {
        Scanner sc = new Scanner(System.in);
        String choice;
        CacheStrategy strategy = null;
        ResultSet rs = null;

        System.out.print(
                "사용할 캐시 알고리즘을 선택하세요\n" +
                "1. FIFO  |  2. LRU  >> "
        );

        choice = sc.nextLine();
        // Strategy Pattern
        switch (choice)
        {
            case "1":
                // first_try.strategy = new FIFO();  ->  이곳에 FIFO 사용
                break;
            case "2":
                strategy = new LRU();
                break;
            default:
                System.out.println("허용하지 않는 선택입니다.");
                break;
        }

        if (strategy != null)
            rs = strategy.doProcess(blocks);

        System.out.println(rs);
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
