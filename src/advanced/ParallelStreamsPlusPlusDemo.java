package advanced;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Person(String FirstName, String lastName, int age) {
    private final static String[] firsts = { "Able", "Bob", "Charlie", "Donna", "Eve", "Fred" };

    private final static String[] lasts = { "Norton", "OHara", "Petersen", "Quincy", "Richardson", "Smith" };

    private final static Random random = new Random();

    public Person() {
        this(firsts[random.nextInt(firsts.length)],
                lasts[random.nextInt(lasts.length)],
                random.nextInt(18, 100));
    }

    @Override
    public final String toString() {
        return "%s, %s (%d)".formatted(FirstName, lastName, age);
    }

}

public class ParallelStreamsPlusPlusDemo {
    public static void main(String[] args) {
        var persons = Stream.generate(Person::new)
                .limit(10)
                .sorted(Comparator.comparing(Person::lastName))
                .toArray();

        for (var person : persons) {
            System.out.println(person);

        }

        System.out.println("_".repeat(30));
        Arrays.stream(persons)
                .limit(10)
                .parallel()
                .forEach(System.out::println);

        System.out.println("_".repeat(30));

        int sum = IntStream.range(1, 101)
                .parallel()
                .reduce(0, Integer::sum);
        System.out.println("sum= " + sum);

        String humptyDumpty = """
                Humpty Dumpty sat on a wall.
                Humpty Dumpty had a great fall.
                All the king's horses and all the king's men
                couldn't put Humpty together again.
                """;

        System.out.println("--------------------------------------------------");
        var words = new Scanner(humptyDumpty).tokens().toList();
        words.forEach(System.out::println);
        System.out.println("--------------------------------------------------");

        var backTogetherAgain = words
                // .parallelStream()
                .stream()
                .reduce((new StringJoiner(" ")), StringJoiner::add, StringJoiner::merge);

        System.out.println(backTogetherAgain);

        System.out.println("_".repeat(30));
        var backTogetherAgainParallel = words
                .parallelStream()
                .collect(Collectors.joining(" "));
        System.out.println(backTogetherAgainParallel);

        System.out.println("_".repeat(30));
        var backTogetherAgain2 = words
                .parallelStream()
                .reduce("",
                        (s1, s2) -> s1
                                .concat(s2)
                                .concat(" "));
        System.out.println(backTogetherAgain2);

        Map<String, Long> lastNameCounts = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .collect(Collectors
                        .groupingBy(Person::lastName,
                                Collectors.counting()));

        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;
        for (long count : lastNameCounts.values()) {
            total += count;
        }
        System.out.println("_".repeat(30));
        System.out.println("Total= " + total);
    }

}
