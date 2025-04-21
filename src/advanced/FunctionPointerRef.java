package advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class PlainOld {
    private static int LAST_ID = 1;
    private int id;

    public PlainOld() {
        super();
        id = LAST_ID++;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}

public class FunctionPointerRef {
    public static void main(String[] args) {
        System.out.println("hey");
        // MethodRefConf();

    }

    private static void MethodRefConf() {
        List<String> names = new ArrayList<>(List.of("Ahmed", "Amjad", "Ali", "Joe", "Trump"));
        names.forEach(System.out::print);
        calculator((a, b) -> a + b, 10, 30);

        calculator(Integer::sum, 10, 30);

        System.out.println(Integer.sum(11, 22));
        calculator(Double::sum, 2.5, 2.3);
        Supplier<PlainOld> ref = PlainOld::new;
        PlainOld p1 = ref.get();
        PlainOld p2 = ref.get();
        System.out.println("*".repeat(20));
        System.out.println("*".repeat(20));
        System.out.println("*".repeat(20));
        // seedPojos(ref, 3);
        seedPlainOldsImproved(ref, 5);
        System.out.println("*".repeat(20));
        System.out.println("*".repeat(20));
        System.out.println("*".repeat(20));
        calculator((s1, s2) -> s1 + s2, "Hello", " World!");
        calculator((s1, s2) -> s1.concat(s2), "Hello", " World!");
        // implicit s1.concat(s2)
        calculator(String::concat, "Hello", " World!");
        // Transform similar to Std::tranform but for string (for now )
        UnaryOperator<String> applyUpperCase = s -> s.toUpperCase();
        String test = "Hello".transform(applyUpperCase);
        System.out.println(test);

        test = test.transform(String::toLowerCase);
        System.out.println(test);

        Function<String, Boolean> f0 = String::isEmpty;
        Boolean w = test.transform(f0);
        System.out.println("w: " + w);
        System.out.println(f0.apply(" a") ? "Empty" : "not empty");
    }

    private static <T> void calculator(BinaryOperator<T> function, T val1, T val2) {
        T result = function.apply(val1, val2);
        System.out.println("result: " + result);
    }

    private static PlainOld[] seedPojos(Supplier<PlainOld> supplier, int count) {
        PlainOld[] result = new PlainOld[count];
        for (int i = 0; i < count; i++) {
            result[i] = supplier.get();
        }
        return result;
    }

    private static PlainOld[] seedPlainOldsImproved(Supplier<PlainOld> supplier, int count) {
        PlainOld[] result = new PlainOld[count];
        Arrays.setAll(result, (_) -> supplier.get());
        return result;
    }

}
