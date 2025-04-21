package intermediate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        int result = calculator((v1, v2) -> v1 + v2, 2, 2);
        System.out.println(result);
        System.out.println(calculator((Double v1, Double v2) -> v1 / v2, 10.0, 3.0));
        var result2 = calculator((String a, String b) -> a.toUpperCase() + " " + b.toUpperCase(), "Ahmed", "Ali");
        System.out.println(result2);
        System.out.println("_".repeat(15));
        var coords = Arrays.asList(
                new double[] { 47.2160, -95.2348 },
                new double[] { 29.1566, -89.2495 },
                new double[] { 35.1556, -90.0659 });
        System.out.println("_".repeat(15));
        coords.forEach(s -> System.out.println(Arrays.toString(s)));
        System.out.println("_".repeat(15));
        processPoint((t1, t2) -> System.out.println(t1 + "," + t2), 35.15151, 24.2424);
        System.out.println("_".repeat(15));
        BiConsumer<Double, Double> p1 = (lat, lng) -> {
            System.out.println("(" + lat + "," + lng + ")");

        };
        System.out.println("_".repeat(15));
        processPoint(p1, 12.12, 11.11);
        System.out.println("_".repeat(15));
        List<String> list = new ArrayList<>(List.of("alpha", "bravo", "charlie", "delta"));
        list.removeIf(s -> s.equalsIgnoreCase("ALPHA"));
        list.forEach(s -> System.out.print(s + " | "));

    }

    public static <T> void processPoint(BiConsumer<T, T> consumer, T t1, T t2) {
        consumer.accept(t1, t2);
    }

    public static <T> T calculator(Operations<T> fun, T v1, T v2) {
        return fun.operate(v1, v2);

    }

    public static <T> T calculatorBuiltIn(BinaryOperator<T> fun, T v1, T v2) {
        return fun.apply(v1, v2);

    }
}