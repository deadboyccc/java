package advanced;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MapViewsMain {
    public static void main(String[] args) {
        Map<String, Integer> hashMap = new HashMap<>();
        String[] names = { "Ahmed", "Ali", "Joe", "Trump", "Biden" };
        for (String string : names) {
            hashMap.put(string, string.length());

        }
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));

        Set<String> keyViews = hashMap.keySet();
        keyViews.forEach((k) -> System.out.printf("Key: %-15s %n", k));

        Set<String> sortedKeyCopies = new TreeSet<>(hashMap.keySet());
        System.out.println(sortedKeyCopies);

        System.out.println("*".repeat(100));
        if (hashMap.containsKey("Ali")) {
            System.out.println("Ali is in!");
        } else {
            System.out.println("nah!");
        }

        // ref view
        keyViews.remove("Ali");
        if (hashMap.containsKey("Ali")) {
            System.out.println("Ali is in!");
        } else {
            System.out.println("nah!");
        }
        sortedKeyCopies.remove("Trump");
        if (hashMap.containsKey("Trump")) {
            System.out.println("Trump is in!");
        } else {
            System.out.println("nah!");
        }
        keyViews.retainAll(List.of("Ahmed", "Joe", "Trump", "Biden"));
        System.out.println("*".repeat(20));
        keyViews.forEach((k) -> System.out.printf("Key: %-15s %n", k));
        System.out.println("*".repeat(20));
        // keyViews.clear();
        System.out.println("*".repeat(20));
        keyViews.forEach((k) -> System.out.printf("Key: %-15s %n", k));
        System.out.println("*".repeat(20));

        var vals = hashMap.values();
        vals.forEach(System.out::println);

        List<Integer> intList = new ArrayList<>(vals);
        intList.sort(Comparator.naturalOrder());
        System.out.println(intList);

        Set<Integer> set = new HashSet<>(vals);
        set.forEach((i) -> System.out.print(i + " "));

        // entry sets | entry = node in HashMap
        var nodeSet = hashMap.entrySet();
        for (var node : nodeSet) {
            System.out.println(nodeSet.getClass().getSimpleName());
            if (node.getKey().length() == node.getValue()) {
            System.out.println(node.getClass().getSimpleName());
                System.out.println("valid!");
            } else {
                System.out.println("Invalid");
            }
        }

    }

    private static void testy(Map<String, Integer> hashMap, Set<String> keyViews) {
        keyViews.add("Ali");
        keyViews.add("joe");
        System.out.println("*".repeat(20));
        keyViews.forEach((k) -> System.out.printf("Key: %-15s %n", k));
        System.out.println("*".repeat(20));

    }

}
