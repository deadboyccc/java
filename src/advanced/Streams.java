package advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {

        // Intermediete Operations
        IntStream.iterate((int) 'A', i -> i <= (int) 'z', i -> i += 1)
                .filter(Character::isAlphabetic)
                // once false = not longer checked
                .map(Character::toUpperCase)
                .distinct()
                // .dropWhile(i -> Character.toUpperCase(i) <= 'E')
                // .takeWhile(i -> i < 'a')
                // .skip(5)
                // .filter(i -> Character.toUpperCase(i) > 'E')
                // .parallel()
                .forEach(c -> System.out.printf("%c ", c));
        System.out.println();
        System.out.println("_".repeat(20));
        Random random = new Random();
        System.out.println();
        Stream.generate(() -> random.nextInt((int) 'A', (int) 'Z' + 1))
                .limit(50)
                .distinct()
                .sorted()
                .forEach(c -> System.out.printf("%c ", c));

        // MapToDouble or primitvies to avoid autoboxing & memory use
        // peek
        System.out.println("_".repeat(20));
        System.out.println();
        Stream.generate(() -> random.nextInt((int) 'A', (int) 'Z' + 1))
                .limit(50)
                .distinct()
                // intermediete but for debugging kind of
                .peek(s -> System.out.printf("---> %c |%n", s))
                .sorted()
                .forEach(c -> System.out.printf("%c ", c));

    }

    private static void StreamsIntro() {
        List<String> bingoPool = new ArrayList<>(75);

        int start = 1;
        for (char c : "BINGO".toCharArray()) {
            for (int i = start; i < (start + 15); i++) {
                bingoPool.add("" + c + i);
                // System.out.println("" + c + i);
            }
            start += 15;
        }
        Collections.shuffle(bingoPool);
        for (int i = 0; i < 15; i++) {
            System.out.println(bingoPool.get(i));
        }
        System.out.println("_".repeat(40));

        // List<String> firstOnes = bingoPool.subList(0, 15);
        List<String> firstOnes = new ArrayList<>(bingoPool.subList(0, 15));
        firstOnes.sort(Comparator.naturalOrder());
        firstOnes.replaceAll(s -> {
            if (s.indexOf('G') == 0 || s.indexOf("O") == 0) {
                String updatedString = s.charAt(0) + "-" + s.charAt(1) + "   ";
                System.out.print(updatedString);
                return updatedString;
            }
            return s;
        });
        System.out.println("_____________".repeat(3));
        for (int i = 0; i < 15; i++) {
            System.out.println(bingoPool.get(i));
        }
        System.out.println("_".repeat(40));
        // stream pipline = LINQ xd
        bingoPool.stream()
                .limit(15)
                .filter(s -> s.charAt(0) == 'G' || s.charAt(0) == 'O')
                .map(s -> s.charAt(0) + "-" + s.charAt(1) + "    ")
                .sorted()
                .forEach(s -> System.out.print(s));

        // stream sources
        System.out.println();
        System.out.println("_".repeat(30));
        String[] strings = { "One", "Two", "Three" };

        var firstTempStream = Arrays.stream(strings)
                .sorted(Comparator.reverseOrder());
        // .forEach(System.out::println);

        System.out.println("_".repeat(30));
        var secondTempStream = Stream.of("Six", "Five", "Four")
                .map(String::toUpperCase);
        // .forEach(System.out::println);
        Stream.concat(secondTempStream, firstTempStream)
                .map(s -> s.charAt(0) + "-" + s)
                .forEach(System.out::println);
        Map<Character, int[]> myMap = new LinkedHashMap<>();
        int bingoIndex = 1;
        for (char c : "BINGO".toCharArray()) {
            int[] numbers = new int[15];
            int labelNo = bingoIndex;
            Arrays.setAll(numbers, i -> i + labelNo);
            myMap.put(c, numbers);
            bingoIndex += 15;
        }

        myMap.entrySet().stream()
                .map(e -> e.getKey() + " has range: " + e.getValue()[0] + " - " + e.getValue()[e.getValue().length - 1])
                .forEach(System.out::println);

        System.out.println("_".repeat(30));
        Random random = new Random();
        // infinite stream 0,1
        Stream.generate(() -> random.nextInt(2))
                .limit(10)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();

        // Streams iterator
        System.out.println("_".repeat(30));
        IntStream.iterate(1, n -> n += 1)
                .limit(20)
                .forEach(s -> System.out.print(s + " "));
        // first N Primes
        System.out.println();
        System.out.println("_".repeat(30));
        IntStream.iterate(1, n -> n += 1)
                .filter(Streams::isPrime)
                .limit(20)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println("_".repeat(30));

        // first Primes under 100
        IntStream.iterate(1, n -> n += 1)
                .limit(100)
                .filter(Streams::isPrime)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println("_".repeat(30));

        // overload Stream dot Iterate
        IntStream.iterate(1, n -> n <= 100, n -> n += 1)
                .filter(Streams::isPrime)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println("_".repeat(20));

        // Range
        // returning stream
        IntStream.rangeClosed(0, 5)
                .parallel()
                .forEach(i -> {
                    System.out.println(i + " " + Thread.currentThread().getName());
                });
    }

    public static boolean isPrime(int wholeNumber) {
        if (wholeNumber <= 2) {
            return (wholeNumber == 2);
        }

        for (int divisor = 2; divisor < wholeNumber; divisor++) {
            if (wholeNumber % divisor == 0) {
                return false;
            }
        }
        return true;
    }

}
