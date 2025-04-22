package advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Ahmed", "Mohammed", "Ali", "Joe", "Biden", "Trump"));
        HashMap<String, Integer> hashMap = new HashMap<>();
        Integer c = 0;
        for (String name : names) {
            // overwrite
            hashMap.put(name, ++c);
        }

        System.out.println("*".repeat(50));
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(50));

        System.out.println(hashMap.get("Ali"));
        System.out.println(hashMap.getOrDefault("Alii", -1));

        System.out.println("*".repeat(50));

        // clearing

        System.out.println("clearing!");
        hashMap.clear();
        var test = hashMap.putIfAbsent("Ali", 151);
        if (test != null) {
            System.out.println("duplicate");
        }
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(50));

        // jdk8
        System.out.println("*".repeat(60));
        hashMap.merge("Ali", 80000000, (oldValue, newValue) -> oldValue + newValue);
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));

        // default methods

        System.out.println("*".repeat(100));

        for (String name : names) {
            // overwrite
            hashMap.put(name, ++c);
        }
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(100));
        // clean start on default methods
        // ifAbsent and ifPresent variants
        hashMap.compute("Trump", (k, v) -> v == null ? 0 : v * v);
        System.out.println("*".repeat(100));
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(100));
        hashMap.replaceAll((k, v) -> {
            if (k == "Ali") {
                return 111111;

            }
            return 22;
        });
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(100));
        hashMap.replace("Ali", 3333);
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(100));

        hashMap.remove("Trump");
        // key,val match
        hashMap.remove("Ali", 3333);
        hashMap.forEach((k, v) -> System.out.printf("%-15s %-15d %n", k, v));
        System.out.println("*".repeat(100));


        //views collection 
        


    }

}
