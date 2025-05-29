package advanced;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
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

        System.out.println("_".repeat(30));

        var test = Stream.generate(Person::new)
                .limit(10)
                .parallel()
                .collect(Collectors.groupingBy(Person::lastName));

        // group by last name= key
        // elements grouped by that key = list of them
        // --- Map<String(lastname) -> List<Person>(who has that last name)> ---
        test.forEach((lastName, people) -> {
            System.out.println("Last name: " + lastName);
            people.forEach(System.out::println);
            System.out.println();
        });

        test.keySet().forEach(System.out::println);
        // not thread safe -> lacks sync
        System.out.println(test.getClass().getName());
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        Map<String, Long> lastNameCountsThreadSafe = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .collect(Collectors
                        .groupingByConcurrent(Person::lastName,
                                Collectors.counting()));

        lastNameCountsThreadSafe.entrySet().forEach(System.out::println);

        long totalConcurrent = 0;
        for (long count : lastNameCountsThreadSafe.values()) {
            totalConcurrent += count;
        }
        System.out.println("_".repeat(30));
        System.out.println("Total= " + totalConcurrent);
        System.out.println("_".repeat(30));
        System.out.println(lastNameCountsThreadSafe.getClass().getName());

        // side effects | not threadSafe map + parallel streams = no
        var lastCounts = new ConcurrentSkipListMap<String, Long>();
        // this is block while the above is't
        // var lastCounts = Collections.synchronizedMap(new TreeMap<String, Long>());
        Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .forEach((person) -> lastCounts.merge(person.lastName(),
                        1L,
                        Long::sum));

        System.out.println(lastCounts);
        long totalSideEffectsCount = 0;
        for (long count : lastCounts.values()) {
            totalSideEffectsCount += count;
        }

        System.out.println("totalSideEffectscount = " + totalSideEffectsCount);

        var threadMap = new ConcurrentSkipListMap<String, Long>();

        System.out.println("_____".repeat(20));
        var persons2 = Stream.generate(Person::new)
                .limit(10_000)
                .parallel()

                // side effects :3
                .peek((p) -> {
                    var threadName = Thread.currentThread().getName().replace("ForkJoinPool.commonPool-worker-",
                            "Thread_");
                    threadMap.merge(threadName, 1L, Long::sum);
                })

                .toArray(Person[]::new);

        System.out.println("total = " + persons2.length);
        System.out.println("_____".repeat(20));
        System.out.println(threadMap);
        System.out.println("_____".repeat(20));
        threadMap.forEach((k, v) -> {
            if (k.equalsIgnoreCase("main")) {
                System.out.println("--->Main: " + v);
                return;
            }
            System.out.printf("%-10s:%5d%n", k, v);

        });
        System.out.println("_____".repeat(20));
        long threadTotal = 0;

        for (long count : threadMap.values()) {
            threadTotal += count;
        }
        System.out.println("Thread counts = " + threadTotal);
    }

}
