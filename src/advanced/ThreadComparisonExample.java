package advanced;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

public class ThreadComparisonExample {

    private static final int NUM_REQUESTS = 10_000;
    private static final int BLOCKING_DURATION_MS = 300; // Simulate 300ms blocking operation

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Thread Comparison Example...");
        System.out.println("Number of requests: " + NUM_REQUESTS);
        System.out.println("Blocking duration per request: " + BLOCKING_DURATION_MS + "ms\n");

        // --- Virtual Threads ---
        long virtualThreadStartTime = System.currentTimeMillis();
        runWithVirtualThreads();
        long virtualThreadEndTime = System.currentTimeMillis();
        long virtualThreadDuration = virtualThreadEndTime - virtualThreadStartTime;
        System.out.printf("Virtual Threads: Completed %d tasks in %dms (average per task: %.2fms)%n",
                NUM_REQUESTS, virtualThreadDuration, (double) virtualThreadDuration / NUM_REQUESTS);

        System.out.println("\n--- Platform Threads (Fixed Pool) ---");

        // --- Platform Threads with Pool Size 200 ---
        int poolSize200 = 200;
        long platformThread200StartTime = System.currentTimeMillis();
        runWithPlatformThreads(poolSize200);
        long platformThread200EndTime = System.currentTimeMillis();
        long platformThread200Duration = platformThread200EndTime - platformThread200StartTime;
        System.out.printf("Platform Threads (Pool Size %d): Completed %d tasks in %dms (average per task: %.2fms)%n",
                poolSize200, NUM_REQUESTS, platformThread200Duration, (double) platformThread200Duration / NUM_REQUESTS);

        // --- Platform Threads with Pool Size 1000 ---
        int poolSize1000 = 1000;
        long platformThread1000StartTime = System.currentTimeMillis();
        runWithPlatformThreads(poolSize1000);
        long platformThread1000EndTime = System.currentTimeMillis();
        long platformThread1000Duration = platformThread1000EndTime - platformThread1000StartTime;
        System.out.printf("Platform Threads (Pool Size %d): Completed %d tasks in %dms (average per task: %.2fms)%n",
                poolSize1000, NUM_REQUESTS, platformThread1000Duration, (double) platformThread1000Duration / NUM_REQUESTS);
    }

    private static void simulateBlockingOperation() {
        try {
            Thread.sleep(BLOCKING_DURATION_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Task interrupted.");
        }
    }

    private static void runWithVirtualThreads() throws InterruptedException {
        // Use try-with-resources for automatic shutdown of the ExecutorService
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(NUM_REQUESTS);

            for (int i = 0; i < NUM_REQUESTS; i++) {
                executor.submit(() -> {
                    simulateBlockingOperation();
                    latch.countDown();
                });
            }
            latch.await(); // Wait for all tasks to complete
        }
    }

    private static void runWithPlatformThreads(int poolSize) throws InterruptedException {
        // Use try-with-resources for automatic shutdown of the ExecutorService
        try (ExecutorService executor = Executors.newFixedThreadPool(poolSize)) {
            CountDownLatch latch = new CountDownLatch(NUM_REQUESTS);

            for (int i = 0; i < NUM_REQUESTS; i++) {
                executor.submit(() -> {
                    simulateBlockingOperation();
                    latch.countDown();
                });
            }
            latch.await(); // Wait for all tasks to complete
        }
    }
}
