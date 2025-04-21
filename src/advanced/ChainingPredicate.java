package advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ChainingPredicate {
    public static void main(String[] args) {
        Predicate<String> p1 = s -> s.equals("JOE");
        Predicate<String> p2 = s -> s.equalsIgnoreCase("JOE");
        Predicate<String> p3 = s -> s.startsWith("J");
        Predicate<String> p4 = s -> s.endsWith("n");

        // combining predicates
        Predicate<String> combinPredicate1 = p1.or(p2);
        System.out.println(combinPredicate1.test("JOE"));

        Predicate<String> combinPredicate2 = p3.and(p4);
        System.out.println(combinPredicate2.test("joe"));

        Predicate<String> combPredicate3 = combinPredicate2.negate();
        System.out.println(combPredicate3.test("joe"));

        // Comparator Helper default methods
        record Person(String fName, String lName) {
        }
        List<Person> list = new ArrayList<>(Arrays.asList(
                new Person("Peter", "Pan"),
                new Person("Peter", "PumpkinEater"),
                new Person("Minnie", "Mouse"),
                new Person("Mickey", "Mouse")));

        list.sort((o1, o2) -> o1.lName.compareTo(o2.lName));
        list.forEach(System.out::println);

        System.out.println("*".repeat(20));
        list.sort(Comparator.comparing(Person::lName));
        list.forEach(System.out::println);

        System.out.println("*".repeat(20));
        list.sort(Comparator.comparing(Person::lName)
                .thenComparing(Person::fName));
        list.forEach(System.out::println);

        System.out.println("*".repeat(20));
        list.sort(Comparator.comparing(Person::lName)
                .thenComparing(Person::fName).reversed());
        list.forEach(System.out::println);
    }

}
