package advanced;

import java.util.Arrays;
import java.util.Random;

public class ParallelStreamsProcessingDemo {
    public static void main(String[] args) {

        // Number of random numbers to generate
        int numbersLength = 100_000_000;

        // Generate an array of random long values
        long[] numbers = new Random().longs(numbersLength,
                1,
                numbersLength)
                .toArray();

        long delta = 0; // To accumulate time difference between serial and parallel
        int iterations = 200; // Number of iterations to average timings

        for (int i = 0; i < iterations; i++) {
            // Measure time for serial average calculation
            long start = System.nanoTime();
            double averageSerial = Arrays.stream(numbers)
                    .average()
                    .orElseThrow();
            long elapsedSerial = System.nanoTime() - start;

            System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n",
                    averageSerial, elapsedSerial,
                    elapsedSerial / 1000000.0);

            // Measure time for parallel average calculation
            start = System.nanoTime();
            double averageParallel = Arrays.stream(numbers)
                    .parallel()
                    .average()
                    .orElseThrow();
            long elapsedParallel = System.nanoTime() - start;

            System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n",
                    averageParallel, elapsedParallel,
                    elapsedParallel / 1000000.0);
            // Accumulate the time difference
            delta += (elapsedSerial - elapsedParallel);
        }
        // Print the average speedup of parallel over serial
        System.out.println("---> Final Result : ");
        System.out.printf("Parallism is [%d] nanos or [%.2f] ms faster on average %n",
                (delta / iterations),
                (delta / 1000000.0 / iterations));
    }

}
