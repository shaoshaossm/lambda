package StreamTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/4 11:15
 */

/**
 * Stream 的操作三个步骤
 *
 * - 创建Stream
 *   - 一个数据源（集合，数组），获取一个流
 *
 * - 中间操作
 *   - 一个中间操作链，对数据源的数据进行处理
 * - 终止操作
 *   - 一个终止操作，执行中间操作链，并产生结果
 */
public class TestStreamAPI1 {

    @Test
    public void test1(){

        // 1.通过Collection 系列集合提供的 stream() 或 parallelStream()
        ArrayList<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        // 2.通过Arrays中的静态方法Stream()获取数组流
        Integer [] integer1 = new Integer[10];
        Stream<Integer> stream2 = Arrays.stream(integer1);

        // 3.通过Stream类中静态方法of()
        Stream<String> stream3 = Stream.of("a", "b", "c");

        // 4.创建无限流
        // 迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);
    }
}
