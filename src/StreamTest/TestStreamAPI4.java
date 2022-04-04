package streamtest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/4 20:25
 */
public class TestStreamAPI4 {
    @Test
    public void test01() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream()
                .map((x) -> x * x)
                .forEach(System.out::println);
    }

    @Test
    public void test02() {
        Integer[] nums = new Integer[]{1, 2, 3, 4};
        Arrays.stream(nums)
                .map((x) -> x * x)
                .forEach(System.out::println);
    }

    List<Employee> emps = Arrays.asList(
            new Employee(101, "Z3", 19, 9999.99, Employee.Status.BUSY),
            new Employee(102, "L4", 20, 7777.77, Employee.Status.FREE),
            new Employee(103, "W5", 35, 6666.66, Employee.Status.BUSY),
            new Employee(104, "Tom", 44, 1111.11, Employee.Status.VOCATION),
            new Employee(105, "Jerry", 60, 4444.44, Employee.Status.VOCATION),
            new Employee(105, "Jerry", 60, 4444.44, Employee.Status.FREE),
            new Employee(105, "Jerry", 60, 4444.44, Employee.Status.BUSY)
    );

    @Test
    public void test03() {
        Optional<Integer> result = emps.stream()
                .map((e) -> 1)
                .reduce(Integer::sum);
        System.out.println(result.get());

    }
}
