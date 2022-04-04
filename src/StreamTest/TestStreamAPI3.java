package streamtest;

import org.junit.Test;
import streamtest.Employee.Status;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/4 15:01
 */
public class TestStreamAPI3 {
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
    public void test01() {
        List<Status> list = Arrays.asList(Status.FREE, Status.BUSY, Status.VOCATION);

        boolean flag1 = list.stream()
                .allMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag1);

        boolean flag2 = list.stream()
                .anyMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag2);

        boolean flag3 = list.stream()
                .noneMatch((s) -> s.equals(Status.BUSY));
        System.out.println(flag3);

        // 避免空指针异常
        Optional<Status> op1 = list.stream()
                .findFirst();
        // 如果Optional为空 找一个替代的对象
        Status s1 = op1.orElse(Status.BUSY);
        System.out.println(s1);

        Optional<Status> op2 = list.stream()
                .findAny();
        System.out.println(op2);

        long count = list.stream()
                .count();
        System.out.println(count);
    }

    @Test
    public void tet2() {
        List<Status> list = Arrays.asList(Status.FREE, Status.BUSY, Status.VOCATION);

        Long count = list.stream()
                .count();
        System.out.println(count);

        Optional<Employee> op1 = emps.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
//                        .max(Comparator.comparingDouble(Employee::getSalary));
        System.out.println(op1.get());

        Optional<Double> op2 = emps.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(op2.get());
    }

    /**
     * 归约：reduce(T identity, BinaryOperator) / reduce(BinaryOperator) 可以将流中的数据反复结合起来，得到一个值
     */
    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op);
    }

    /**
     * 收集：collect 将流转换成其他形式；接收一个 Collector 接口的实现，用于给流中元素做汇总的方法
     */
    @Test
    public void test4() {
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList()
//                .collect(Collectors.toSet()
                );
        list.forEach(System.out::println);
        System.out.println("----------------------------");
        HashSet<String> hs = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);
    }

    @Test
    public void test04() {
        //分组
        Map<Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);

        //多级分组
        Map<Status, Map<String, List<Employee>>> mapMap = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (((Employee) e).getAge() <= 35) {
                        return "青年";
                    } else if (((Employee) e).getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(mapMap);

        //分区
        Map<Boolean, List<Employee>> listMap = emps.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 4321));
        System.out.println(listMap);
    }

    @Test
    public void test05() {
        //总结
        DoubleSummaryStatistics dss = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
        System.out.println(dss.getMin());
        System.out.println(dss.getSum());
        System.out.println(dss.getCount());
        System.out.println(dss.getAverage());

        //连接
        String str = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("-")); //可传入分隔符
        System.out.println(str);
    }

    @Test
    public void test03() {
        //总数
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        //平均值
        Double avg = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //总和
        Double sum = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        //最大值
        Optional<Employee> max = emps.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());
        System.out.println("-------------");
        Optional<Double> max2 = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));
        System.out.println(max2.get());
        //最小值
        Optional<Double> min = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
    }

}
