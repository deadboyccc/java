package intermediate;

import java.util.ArrayList;
import java.util.List;

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

    }
}
