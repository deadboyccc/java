import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AsyncPrimeCheck {

    // Define the number to check as a constant for better readability and
    // maintainability
    private static final int NUMBER_TO_CHECK = 313_222_313;

    private static boolean isPrime(int x) {
        if (x <= 1)
            return false;
        // Optimization: check divisibility only up to the square root of x
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        log("Main started");

        // Create a ThreadFactory for virtual threads with a descriptive naming pattern
        ThreadFactory virtualThreadFactory = Thread.ofVirtual()
                .name("prime-checker-", 1) // More specific naming for virtual threads
                .factory();

        // Use try-with-resources to ensure the ExecutorService is shut down properly
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(virtualThreadFactory)) {

            // Submit the prime checking task asynchronously
            CompletableFuture<Boolean> primeFuture = CompletableFuture.supplyAsync(
                    () -> {
                        log("Calculating primality for " + NUMBER_TO_CHECK);
                        return isPrime(NUMBER_TO_CHECK);
                    },
                    executor // Specify the executor to use for this task
            );

            log("Doing other work while prime is being checked...");
            // Simulate some other work in the main thread (optional, for demonstration)
            try {
                Thread.sleep(8000); // Simulate x ms of other work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main thread interrupted.");
            }

            // Chain a completion stage to handle the result when it's ready
            primeFuture
                    .thenAcceptAsync( // Use thenAcceptAsync to process the result on a virtual thread
                            isPrime -> log(NUMBER_TO_CHECK + (isPrime ? " is prime" : " is not prime")),
                            executor // Specify the executor for the callback
                    )
                    .exceptionally(ex -> { // Handle any exceptions that occur during the async computation
                        System.err.println("Error [" + Thread.currentThread().getName() + "]: " + ex.getMessage());
                        return null; // Return null to complete the exceptionally stage (no specific return needed
                                     // for thenAcceptAsync)
                    })
                    .join(); // Block the main thread until the entire chain completes

        } // ExecutorService is automatically closed here

        log("Program finished");
    }

    private static void log(String msg) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), msg);
    }
}
