package advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelProcessesDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int numbersLength = 100_000;
        long[] numbers = new Random().longs(numbersLength,
                1,
                numbersLength)
                .toArray();

        long sum = Arrays.stream(numbers).sum();
        System.out.println("Sum= " + sum);

        System.out.println("____".repeat(20));

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores: " + cores);
        var threadPool = Executors.newWorkStealingPool(cores);

        List<Callable<Long>> tasks = new ArrayList<>();

        // number of tasks
        int taskNo = 10;

        // total work size ( array length) / number of tasks = split per task
        int splitCount = numbersLength / taskNo;

        for (int i = 0; i < taskNo; i++) {
            int start = i * splitCount;
            int end = start + splitCount;
            tasks.add(() -> {
                long taskSum = 0;
                for (int j = start; j < end; j++) {
                    taskSum += (long) numbers[j];
                }
                return taskSum;
            });
        }
        List<Future<Long>> futures = threadPool.invokeAll(tasks);
        long taskSum = 0;
        for (Future<Long> future : futures) {
            taskSum += future.get();

        }
        System.out.println("parallel Sum= " + taskSum);

        threadPool.shutdown();
        System.out.println(threadPool.getClass().getName());
    }

}
