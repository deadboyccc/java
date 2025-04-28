package advanced;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Mathy {
    public static void main(String[] args) {
        int maxMinusFive = Integer.MAX_VALUE - 5;
        // safer
        for (int j = 0, id = maxMinusFive; j < 10; Math.incrementExact(id), j++) {
            System.out.printf("id %,d%n", id);
        }

        // catching under/over flows
        System.out.println(Math.abs(-50));
        // int overflow
        System.out.println(Math.abs(Integer.MIN_VALUE));
        // exact
        // System.out.println(Math.absExact(Integer.MIN_VALUE));
        // overloading with long
        System.out.println(Math.abs((long) Integer.MIN_VALUE));

        System.out.println(Math.min(1, 20));
        System.out.println(Math.max(1, 20));
        System.out.println(Math.pow(2, 10));
        // round
        System.out.println(Math.round(10.2));
        System.out.println(Math.round(10.9));
        System.out.println(Math.ceil(20.2));
        System.out.println(Math.floor(20.2));
        System.out.println(Math.sqrt(100));
        System.out.println(Math.pow(10, 3));
        // alg +trig + constants
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            // [0,1)
            list.add((int) (Math.random() * 10) + 1);
        }
        System.out.println(Collections.min(list));
        System.out.println(Collections.max(list));

        for (int i = 0; i < 10; i++) {
            System.out.printf("%1$d = %1$c%n", new Random().nextInt((int) 'A', (int) 'Z' + 1));
        }
        System.out.println("_".repeat(20));
        new Random().ints()
                .limit(20)
                .forEach(System.out::println);
        System.out.println("_".repeat(20));

        System.out.println("_".repeat(20));
        new Random().ints(0, 11)
                .limit(20)
                .forEach(System.out::println);
        System.out.println("_".repeat(20));

        // finite stream
        System.out.println("_".repeat(20));
        new Random().ints(10, 0, 11)
                .forEach(System.out::println);
        System.out.println("_".repeat(20));
        // stream size
        System.out.println("_".repeat(20));
        new Random().ints(10)
                .forEach(System.out::println);
        System.out.println("_".repeat(20));

        // seeding
        final long nanoTime = System.nanoTime();
        Random pseudoRandom = new Random(nanoTime);

        System.out.println("_".repeat(20));
        pseudoRandom.ints(10, 0, 10)
                .forEach((i) -> System.out.print(i + " "));
        System.out.println();
        System.out.println("_".repeat(20));

        Random notReallyRandom = new Random(nanoTime);
        notReallyRandom.ints(10, 0, 10)
                .forEach((i) -> System.out.print(i + " "));
        System.out.println();
        System.out.println("_".repeat(20));

        // big decimal

        double policyAmount = 100_000_000;
        int beneficiaries = 3;
        float percentageFloat = 1.0f / beneficiaries;
        double percentageDouble = 1.0 / beneficiaries;
        System.out.printf("Payout = %,.2f%n", policyAmount * percentageFloat);
        System.out.printf("Payout = %,.2f%n", policyAmount * percentageDouble);

        double totalUsingFloats = policyAmount - ((policyAmount * percentageFloat) * beneficiaries);
        System.out.println(totalUsingFloats);

        double totalUsingDouble = policyAmount - ((policyAmount * percentageDouble) * beneficiaries);
        System.out.println(totalUsingDouble);

        String[] tests = { "15.456", "8", "10000.000001", ".123" };
        BigDecimal[] bds = new BigDecimal[tests.length];
        Arrays.setAll(bds, i -> new BigDecimal(tests[i]));

        System.out.printf("%-14s %-15s %-8s %s%n", "Value", "Unscaled Value", "Scale", "Precision");

        for (var bd : bds) {
            System.out.printf("%-15s %-15d %-8d %d%n",
                    bd, bd.unscaledValue(), bd.scale(), bd.precision());
        }

        double[] doubles = { 15.456, 8, 10000.000001, .123 };
        Arrays.setAll(bds, i -> BigDecimal.valueOf(doubles[i]));
        System.out.println("_".repeat(200));

        System.out.printf("%-14s %-15s %-8s %s%n", "Value", "Unscaled Value", "Scale", "Precision");

        for (var bd : bds) {
            System.out.printf("%-15s %-15d %-8d %d%n",
                    bd, bd.unscaledValue(), bd.scale(), bd.precision());
        }

        BigDecimal test1 = new BigDecimal("1.12345123451234512345");
        BigDecimal test2 = BigDecimal.valueOf(1.12345123451234512345);

        System.out.printf("%-30s %-30s %-8s %s%n", "Value", "Unscaled Value", "Scale", "Precision");

        System.out.printf("%-30s %-30d %-8d %d%n",
                test1, test1.unscaledValue(), test1.scale(), test1.precision());

        System.out.printf("%-30s %-30d %-8d %d%n",
                test2, test2.unscaledValue(), test2.scale(), test2.precision());

        System.out.println("_".repeat(20));

        System.out.printf("%-14s %-15s %-8s %s%n", "Value", "Unscaled Value", "Scale", "Precision");

        for (var bd : bds) {

            System.out.printf("%-15s %-15d %-8d %d%n",
                    bd, bd.unscaledValue(), bd.scale(), bd.precision());
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            System.out.printf("%-15s %-15d %-8d %d%n",
                    bd, bd.unscaledValue(), bd.scale(), bd.precision());
        }
        System.out.println("will try to skip some features I won't be using any time soon!");
    }

}
