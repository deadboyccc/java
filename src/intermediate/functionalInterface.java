package intermediate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class functionalInterface {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>(List.of("alpha", "bravo", "charlie", "delta"));
        for (String str : list)
            System.out.println(str.toUpperCase());

        System.out.println("_".repeat(20));

        list.forEach((s) -> System.out.println(s));
        System.out.println("_".repeat(20));
        final String postFix = "HEHEHE";
        list.forEach((String element) -> {
            // ref type but string are immutable so copied to another block
            element += postFix;
            System.out.println(element);

        });
        System.out.println("_".repeat(20));
        System.out.println(list);
        System.out.println("_".repeat(20));
        System.out.println(list);
        UnaryOperator<String> stringTranformer = s -> String.valueOf(s.charAt(0));
        UnaryOperator<String> stringTransformer2 = s -> s.substring(1);

        list.replaceAll(stringTransformer2);
        System.out.println(list);

        String[] emptyStrings = new String[10];
        System.out.println(Arrays.toString(emptyStrings));
        Arrays.fill(emptyStrings, "empty");
        System.out.println(Arrays.toString(emptyStrings));

        String[] newEmptyStrings = new String[10];
        Arrays.setAll(newEmptyStrings, i -> "." + i + ".");
        System.out.println(Arrays.toString(newEmptyStrings));

        Arrays.setAll(newEmptyStrings, (i) -> "." + i + "."
                + switch (i) {
                    case 0 -> "Zero";
                    case 1 -> "One";
                    case 2 -> "Two";
                    case 3 -> "Three";
                    default -> "_";
                });

        System.out.println("*".repeat(22));
        Supplier<String> p1 = () -> "hello";

        final String teString = p1.get();

        System.out.println("*".repeat(22));

    }
}
