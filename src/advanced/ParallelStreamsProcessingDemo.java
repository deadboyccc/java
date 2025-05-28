package advanced;

import java.util.Arrays;
import java.util.Random;

public class ParallelStreamsProcessingDemo {
    public static void main(String[] args) {
        int numbersLength = 100_000_000;
        long[] numbers = new Random().longs(numbersLength, 1, numbersLength).toArray();

        long delta = 0;
        int iterations = 200;

        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            double averageSerial = Arrays.stream(numbers).average().orElseThrow();
            long elapsedSerial = System.nanoTime() - start;

            // System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n",
            // averageSerial, elapsedSerial,
            // elapsedSerial / 1000000.0);

            start = System.nanoTime();
            double averageParallel = Arrays.stream(numbers).parallel().average().orElseThrow();
            long elapsedParallel = System.nanoTime() - start;

            // System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n",
            // averageParallel, elapsedParallel,
            // elapsedParallel / 1000000.0);
            delta += (elapsedSerial - elapsedParallel);
        }
        System.out.printf("Parallism is [%d] nanos or [%.2f] ms faster on average %n", delta / iterations,
                delta / 1000000.0 / iterations);
    }

}
