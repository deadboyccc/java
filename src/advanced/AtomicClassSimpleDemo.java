package advanced;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicClassSimpleDemo {
    // Global setting for number of registrations
    private static final int REGISTRATION_COUNT = 102;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicId = new AtomicInteger(1);
        int[] nonAtomicId = { 1 }; // shared mutable state (not thread-safe)

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        Runnable atomicRegister = () -> {
            int id = atomicId.getAndIncrement();
            System.out.printf("Atomic registration ID: %d by %s%n", id, Thread.currentThread().getName());
        };

        Runnable nonAtomicRegister = () -> {
            int id = nonAtomicId[0]++; // not thread-safe!
            System.out.printf("Non-atomic registration ID: %d by %s%n", id, Thread.currentThread().getName());
        };

        // Submit atomic registration tasks
        for (int i = 0; i < REGISTRATION_COUNT; i++) {
            executor.submit(atomicRegister);
        }

        // Submit non-atomic registration tasks
        for (int i = 0; i < REGISTRATION_COUNT; i++) {
            executor.submit(nonAtomicRegister);
        }

        // Shut down and wait for all tasks to finish
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.err.println("Executor did not terminate in time.");
        }

        // Summary
        int expectedId = REGISTRATION_COUNT + 1;

        System.out.println("\n=== Summary ===");
        System.out.printf("Expected atomic ID to be %d, actual: %d%n", expectedId, atomicId.get());
        System.out.printf("Expected non-atomic ID to be %d, actual: %d%n", expectedId, nonAtomicId[0]);

        if (nonAtomicId[0] < expectedId) {
            System.out.println("⚠️  Race condition occurred in non-atomic registration.");
        } else {
            System.out.println("✅ No visible race condition, but non-atomic logic is still unsafe.");
        }
    }
}
