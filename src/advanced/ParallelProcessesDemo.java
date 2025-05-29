package advanced;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

class RecursiveSumTask extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    private final int division;

    public RecursiveSumTask(long[] numbers, int start, int end, int division) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.division = division;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= (numbers.length / division)) {
            System.out.println(start + " : " + end);

            long sum = 0;

            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;

        } else {

            int mid = (start + end) / 2;

            RecursiveSumTask leftTask = new RecursiveSumTask(numbers, start, mid, division);
            RecursiveSumTask rightTask = new RecursiveSumTask(numbers, mid, end, division);

            leftTask.fork();
            rightTask.fork();

            return leftTask.join() + rightTask.join();
        }
    }

}

public class ParallelProcessesDemo {
    private static final int BLOCKING_DURATION_MS = 500;

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
        ForkJoinPool threadPool = ForkJoinPool.commonPool();

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

        System.out.println("Parallelism Level = " + threadPool.getParallelism());
        System.out.println("Pool Size = " + threadPool.getPoolSize());
        System.out.println("Steal Size = " + threadPool.getStealCount());
        System.out.println("Free Mem = " + Runtime.getRuntime().freeMemory());

        long taskSum = 0;
        for (Future<Long> future : futures) {
            taskSum += future.get();

        }
        System.out.println("parallel Sum= " + taskSum);

        RecursiveTask task = new RecursiveSumTask(numbers, 0, numbersLength, 8);
        long forkJoinSum = (long) threadPool.invoke(task);
        System.out.println("forkJoinSum= " + forkJoinSum);
        threadPool.shutdown();
        System.out.println(threadPool.getClass().getName());
    }

    private static void simulateBlockingOperation() {
        try {
            Thread.sleep(BLOCKING_DURATION_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Task interrupted.");
        }
    }

}
