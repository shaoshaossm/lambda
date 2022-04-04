package streamtest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/4 11:15
 */

// 中间操作
public class TestStreamAPI2 {

    List<Employee> emps = Arrays.asList(
            new Employee(101, "Z3", 19, 9999.99, Employee.Status.BUSY),
            new Employee(102, "L4", 20, 7777.77,Employee.Status.FREE),
            new Employee(103, "W5", 35, 6666.66,Employee.Status.BUSY),
            new Employee(104, "Tom", 44, 1111.11,Employee.Status.VOCATION),
            new Employee(105, "Jerry", 60, 4444.44,Employee.Status.VOCATION),
            new Employee(105, "Jerry", 60, 4444.44,Employee.Status.FREE),
            new Employee(105, "Jerry", 60, 4444.44,Employee.Status.BUSY)
    );

    @Test
    public void test00() {
        // 中间操作：不会执行任何操作
        Stream<Employee> stream = emps.stream()
                .filter((e) -> {
                    System.out.println("Stream API 中间操作");
                    return e.getAge() > 35;
                });
        // 终止操作：一次性执行全部内容，即“惰性求值”
        stream.forEach(System.out::println);

    }

    /**
     * 中间操作：
     * <p>
     * filter：接收 Lambda ，从流中排除某些元素
     * limit：截断流，使其元素不超过给定数量
     * skip(n)：跳过元素，返回一个舍弃了前n个元素的流；若流中元素不足n个，则返回一个空流；与 limit(n) 互补
     * distinct：筛选，通过流所生成的 hashCode() 与 equals() 取除重复元素
     */
    // 内部迭代
    @Test
    public void test01() {
        emps.stream()
                .filter((x) -> x.getAge() > 35)
                .limit(3)  //短路？达到满足不再内部迭代
                .distinct()
                .skip(1)
                .forEach(System.out::println);
    }

    /**
     * 映射
     * - map：接收 Lambda ，将元素转换为其他形式或提取信息；接受一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
     * - flatMap：接收一个函数作为参数，将流中每一个值都换成另一个流，然后把所有流重新连接成一个流
     */
    @Test
    public void test2() {
        List<String> list = Arrays.asList("cc", "bb", "aa");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);
        System.out.println("-----------------------------");
        emps.stream()
                .map((Employee::getName))
                .forEach(System.out::println);
        System.out.println("-----------------------------");
        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter);

        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });
        System.out.println("-----------------------------");
        Stream<Character> characterStream = list.stream()
                .flatMap(TestStreamAPI2::filterCharacter);
        characterStream.forEach(System.out::println);

    }

    public static Stream<Character> filterCharacter(String str) {
        ArrayList<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray(
        )) {
            list.add(ch);
        }
        return list.stream();
    }
    /**
     * sorted()：自然排序
     * sorted(Comparator c)：定制排序
     */
    @Test
    public void test3(){
        List<String> list = Arrays.asList("cc", "bb", "aa");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("-----------------------");
        emps.stream()
                .sorted((e1,e2)->{
                    if (e1.getAge().equals(e2.getAge())){
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return -e1.getAge().compareTo(e2.getAge());
                    }
                }).forEach(System.out::println);
    }

}

