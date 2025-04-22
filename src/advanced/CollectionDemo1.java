package advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionDemo1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(12);
        list.addAll(0, Arrays.asList("hello", "Biden", "bye", "Test", "hi", "bye"));
        System.out.println(list);
        Collections.shuffle(list);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);

        var subList = list.subList(1, list.size() - 2);
        System.out.println(subList);
        var isTrue = list.containsAll(subList);
        System.out.println(isTrue);

    }

}
