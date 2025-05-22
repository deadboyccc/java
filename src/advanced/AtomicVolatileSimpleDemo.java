package advanced;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// encapsulates an atomic Integer counter with thread-safe(non-sync) increment/decrement operations
class AtomicIntegerExperiment {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public int increment() {
        return atomicInteger.incrementAndGet();
    }

    public void decrement() {
        atomicInteger.decrementAndGet();
    }

    public int get() {
        return atomicInteger.get();
    }
}

public class AtomicVolatileSimpleDemo {

    public static void main(String[] args) {
        AtomicIntegerExperiment experiment = new AtomicIntegerExperiment();
        Random random = new Random();

        // defining the task
        Runnable task = () -> {
            // ANSI escape colors for terminal output
            String[] colors = {
                    "\u001B[31m", // red
                    "\u001B[32m", // green
                    "\u001B[33m", // yellow
                    "\u001B[34m", // blue
                    "\u001B[35m" // magenta
            };

            // pick color based on thread ID
            String color = colors[(int) (Thread.currentThread().threadId() % colors.length)];

            // the main task loop
            for (int i = 0; i < 10; i++) {

                // atomically increment and capture the returned value
                int newValue = experiment.increment();

                // print updated value immediately after incrementing
                System.out.println(color + "Thread " + Thread.currentThread().getName() +
                        " incremented value to: " + newValue + "\u001B[0m");

                // randomized sleep to simulate variable workload
                try {
                    Thread.sleep(random.nextInt(50, 150));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // creating threads with a fixed pool of size 10
        ExecutorService executor = Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setName("T" + t.threadId());
            return t;
        });

        // submitting the task 10 times (10 threads) = 100 runs
        for (int i = 0; i < 10; i++) {
            executor.submit(task);
        }

        // graceful shutdown: stops accepting new tasks
        executor.shutdown();

        // waits for existing tasks to complete
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
