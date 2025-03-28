package intro;

import java.util.LinkedList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int lowBoundInt = Integer.MIN_VALUE;
        int highBoundInt = Integer.MAX_VALUE;
        System.out.println(lowBoundInt);
        System.out.println(highBoundInt);
        byte lowBoundByte = Byte.MIN_VALUE;
        System.out.println(lowBoundByte);
        System.out.println(Long.SIZE);
        System.out.println(Long.MAX_VALUE);
        Human test = new Human("Joe", Long.MAX_VALUE);
        System.out.println(test);
        System.out.println(factorial(5));
        LinkedList<Human> humansList = new LinkedList<>();
        humansList.add(test);
        humansList.add(new Human("doe", Byte.MAX_VALUE));
        for (Human human : humansList) {
            System.out.println(human);
        }
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("a", "first");
        testMap.put("b", "second");
        System.out.println(testMap.get("a"));

    }

    public static long factorial(int n) {
        if (n == 1 || n == 0)
            return 1;

        return n * factorial(n - 1);

    }
}