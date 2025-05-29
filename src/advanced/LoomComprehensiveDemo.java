package advanced;

// Project Loom Comprehensive Example (OpenJDK)
// Demonstrates usage of Virtual Threads, Structured Concurrency, and Scoped Values.

import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.StructuredTaskScope;
import java.lang.ScopedValue; // This is a preview API

public class LoomComprehensiveDemo {

    // ScopedValue is a preview API for propagating immutable data implicitly
    // within a dynamic scope.
    static final ScopedValue<String> USERNAME = ScopedValue.newInstance();

    /**
     * Simulates fetching data with a delay.
     * This method is "blocking" and ideal for demonstrating Virtual Threads.
     *
     * @param name        The name of the data being fetched.
     * @param delayMillis The simulated delay in milliseconds.
     * @return A string indicating the completion or interruption.
     */
    public static String fetchData(String name, int delayMillis) {
        try {
            System.out.println(Thread.currentThread().getName() + " starting fetchData for " + name + " with delay "
                    + delayMillis + "ms.");
            Thread.sleep(delayMillis);
            return name + " completed after " + delayMillis + "ms";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return name + " was interrupted unexpectedly";
        }
    }

    /**
     * Demonstrates Structured Concurrency using StructuredTaskScope.
     * It ensures that child tasks are managed and their lifecycle is tied to the
     * parent's.
     * This example uses ShutdownOnFailure, meaning if one task fails, others are
     * cancelled.
     */
    public static void structuredConcurrencyExample() {
        System.out.println("\n--- Structured Concurrency Example ---");
        // ScopedValue is bound for the duration of this specific task scope operation
        ScopedValue.where(USERNAME, "structuredUser").run(() -> {
            // StructuredTaskScope.ShutdownOnFailure ensures that if any subtask
            // throws an exception, all other running subtasks are cancelled,
            // and the exception is propagated when join() is called.
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // Forking tasks to run concurrently within the structured scope
                // These tasks will inherit the ScopedValue 'USERNAME'
                StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> {
                    System.out.println("ScopedValue in Task1: " + USERNAME.get());
                    return fetchData("StructuredTask1", 500);
                });
                StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> {
                    System.out.println("ScopedValue in Task2: " + USERNAME.get());
                    return fetchData("StructuredTask2", 1000);
                });

                try {
                    // Waits for all forked tasks to complete.
                    // If any task failed, it will propagate the exception here.
                    scope.join();
                    // Throws an exception if any of the subtasks failed.
                    scope.throwIfFailed();

                    // If we reach here, all tasks completed successfully.
                    System.out.println("Structured Task Results:");
                    System.out.println("  " + task1.get());
                    System.out.println("  " + task2.get());
                } catch (ExecutionException e) {
                    // An exception occurred in one of the forked tasks
                    System.err.println("Structured tasks failed: " + e.getCause().getMessage());
                    // e.printStackTrace(); // For detailed debugging
                } catch (InterruptedException e) {
                    System.err.println("Structured tasks were interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); // Re-interrupt the current thread
                }
            } // The scope is closed here, ensuring all forked threads are terminated.
        });
    }

    /**
     * Demonstrates the creation and use of Virtual Threads.
     * Virtual threads are very lightweight and can be created in large numbers.
     */
    public static void virtualThreadsExample() throws InterruptedException {
        System.out.println("\n--- Virtual Threads Example ---");
        // Creates an ExecutorService where each submitted task runs on a new virtual
        // thread.
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final int index = i;
            tasks.add(() -> {
                // Print the current thread to show it's a Virtual Thread
                String threadInfo = "Task " + index + ": Running on " + Thread.currentThread();
                // Simulate some work
                Thread.sleep(100 + (index * 50));
                return threadInfo + " and completed work.";
            });
        }

        // Invoke all tasks and wait for them to complete
        List<Future<String>> results = executor.invokeAll(tasks);

        System.out.println("Virtual Thread Task Results:");
        for (Future<String> result : results) {
            try {
                System.out.println("  " + result.get()); // get() retrieves the result of the callable
            } catch (ExecutionException e) {
                System.err.println("  Task failed: " + e.getCause().getMessage());
            }
        }

        // It's crucial to close the executor to release resources.
        // In newer Java versions, try-with-resources can be used for ExecutorService.
        executor.close();
        System.out.println("Virtual threads example finished.");
    }

    /**
     * Combines Virtual Threads and Scoped Values.
     * A ScopedValue is bound, and then a task is submitted to a Virtual Thread
     * executor, demonstrating that the ScopedValue context is propagated.
     *
     * @param exampleName A name for the example (e.g., "Minimal1", "Minimal2").
     * @param dataName    The name of the data to fetch.
     * @param delay       The delay for fetching data.
     * @param username    The username to bind to the ScopedValue.
     * @throws Exception If there's an issue with thread execution.
     */
    public static void combinedVirtualThreadsAndScopedValueExample(
            String exampleName, String dataName, int delay, String username) throws Exception {
        System.out.println("\n--- Combined Example (" + exampleName + ") ---");
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // Bind the USERNAME ScopedValue for the duration of the 'run' lambda.
        // Any code executed within this lambda, including tasks submitted
        // to the virtual thread executor, will see this bound value.
        ScopedValue.where(USERNAME, username).run(() -> {
            System.out.println("Outer scope (main thread context for submission): USERNAME is " + USERNAME.get());

            // Submit a task to run on a Virtual Thread.
            // The ScopedValue context is propagated to the Virtual Thread.
            Future<String> result = executor.submit(() -> {
                // This code runs on a Virtual Thread, and it can access USERNAME.get()
                System.out.println(Thread.currentThread().getName() + " accessing ScopedValue: " + USERNAME.get());
                return USERNAME.get() + "-data:" + fetchData(dataName, delay);
            });

            try {
                // Wait for the Virtual Thread to complete and get its result.
                System.out.println("Result from combined example: " + result.get());
            } catch (ExecutionException e) {
                System.err.println("Combined example task failed: " + e.getCause().getMessage());
            } catch (InterruptedException e) {
                System.err.println("Combined example task interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        });

        // Always close the executor when done to shut down its threads.
        executor.close();
    }

    public static void main(String[] args) throws Exception {
        // IMPORTANT: To compile and run this code, you need to enable preview features:
        // Compile: javac --enable-preview --release 21 LoomComprehensiveDemo.java
        // Run: java --enable-preview LoomComprehensiveDemo

        virtualThreadsExample();
        structuredConcurrencyExample();

        // Using the refactored combined example method
        combinedVirtualThreadsAndScopedValueExample("Minimal1", "Data A", 100, "userAlice");
        combinedVirtualThreadsAndScopedValueExample("Minimal2", "Data B", 150, "userBob");
    }
}

/*
 * Documentation of Features:
 *
 * 1. Virtual Threads (JEP 444 - Final in JDK 21):
 * - Lightweight, user-mode threads managed by the JVM, not the OS kernel.
 * - Mapped by the JVM to a smaller number of platform (OS) threads.
 * - Excellent for I/O-bound operations (like fetchData in this example)
 * because they allow for highly concurrent applications without
 * the resource overhead of traditional platform threads.
 * - Created easily via `Executors.newVirtualThreadPerTaskExecutor()`
 * or `Thread.ofVirtual().start()`.
 *
 * 2. Structured Concurrency (JEP 453 - Final in JDK 21):
 * - Simplifies concurrent programming by treating a group of related tasks
 * as a single unit of work.
 * - Guarantees that concurrent tasks complete or fail together. The parent
 * thread waits for all child threads to complete.
 * - Facilitates better error handling, cancellation, and observability
 * of concurrent operations.
 * - Implemented via `StructuredTaskScope` (e.g., `ShutdownOnFailure`,
 * `ShutdownOnSuccess`).
 *
 * 3. Scoped Values (JEP 446 - Final in JDK 21):
 * - A safer, more performant, and more structured alternative to `ThreadLocal`.
 * - Allows immutable data to be implicitly and efficiently propagated
 * down a call stack to child threads (including virtual threads) within a
 * dynamic scope.
 * - The value is available only for the duration of the
 * `ScopedValue.where().run()` block.
 * - The value is automatically cleaned up when the scope exits.
 *
 * 4. Continuations:
 * - Not a public API, but the underlying mechanism that powers Virtual Threads.
 * - Used internally by the JVM to suspend and resume the execution of
 * Virtual Threads efficiently when they block on I/O.
 * - This allows a Virtual Thread to "yield" its underlying platform thread
 * when it blocks, freeing up the platform thread to run other Virtual Threads.
 */