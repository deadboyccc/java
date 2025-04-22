package advanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>(List.of("Ahmed", "Ali", "Joe"));
        List<String> list2 = new ArrayList<>(List.of("Ahmed", "Ali", "Joe", "Sam"));

        // elements must implement Comparable
        NavigableSet<String> sorted = new TreeSet<>(list1);
        sorted.addAll(list2);
        sorted.forEach(System.out::println);

        // sorted

        NavigableSet<Integer> sortedTree = new TreeSet<>(List.of(8, 2, 4, 1, 5, 100, 20, 25, 113, 0));
        System.out.println(sortedTree);

        // min&max thro collection utils
        Integer min = Collections.min(sortedTree);
        Integer max = Collections.max(sortedTree);
        System.out.println("min: " + min);
        System.out.println("max: " + max);

        // prefered way cuz it's sorted : |
        Integer first = sortedTree.first();
        Integer last = sortedTree.last();
        System.out.println("first: " + first);
        System.out.println("last: " + last);

        // polling
        NavigableSet<Integer> sortedTreeCopy = new TreeSet<>(sortedTree);
        var f = sortedTreeCopy.pollFirst();
        var l = sortedTreeCopy.pollLast();
        System.out.printf("first: %d , last = %d\n", f, l);
        sortedTreeCopy.forEach(System.out::println);

        Integer a = 0;
        Integer b = 100000;
        Integer c = 25;

        // Navigation methods celing & floor
        System.out.println("_".repeat(20));
        for (Integer integer : List.of(a, b, c)) {
            System.out.printf("celing(%d)=%d%n", integer, sortedTreeCopy.ceiling(integer));
            System.out.printf("higher(%d)=%d%n", integer, sortedTreeCopy.higher(integer));

        }
        System.out.println("_".repeat(20));

        System.out.println("_".repeat(20));
        for (Integer integer : List.of(a, b, c)) {
            System.out.printf("floor(%d)=%d%n", integer, sortedTreeCopy.floor(integer));
            System.out.printf("lower(%d)=%d%n", integer, sortedTreeCopy.lower(integer));

        }
        System.out.println("_".repeat(20));

        // sets returning methods on sets
        // backed by original set (refs)
        NavigableSet<Integer> descSet = sortedTreeCopy.descendingSet();
        System.out.println(descSet);
        System.out.println("_".repeat(20));

        // polling and testing backing
        System.out.printf("REMOVING :%d%n ", descSet.pollLast());
        System.out.println("POLLING & BACKING (REFS)");
        System.out.println("_".repeat(20));
        descSet.forEach(System.out::println);
        System.out.println("_".repeat(20));
        sortedTreeCopy.forEach(System.out::println);
        System.out.println("_".repeat(20));

        System.out.println("_".repeat(20));

        // headset subset > subset exxclusive
        Integer twentyFive = 25;
        // true = >= inclusive
        var headset = sortedTreeCopy.headSet(twentyFive, true);
        headset.forEach(System.out::println);
        System.out.println("_".repeat(20));

        // tail subset <= subset inclusive
        var tail = sortedTreeCopy.tailSet(twentyFive);
        tail.forEach(System.out::println);
        System.out.println("_".repeat(20));

        // subsets by elements bounds
        sortedTreeCopy.forEach(System.out::println);

        System.out.println("|".repeat(20));
        // (in,ex)
        var subSset = sortedTreeCopy.subSet(Integer.valueOf(5),true, Integer.valueOf(25),true);
        System.out.println(subSset);

    }

}
