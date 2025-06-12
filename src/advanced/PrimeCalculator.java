import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PrimeCalculator {

  // Callable task to sieve a specific segment of the BitSet.
  // Each task takes a portion of the BitSet and marks composite numbers
  // within that segment based on the base primes found by the main thread.
  static class SieveSegmentTask implements Callable<Void> {
    private final BitSet isPrime; // The shared BitSet representing primality
    private final long startIndex; // Inclusive start index of the segment
    private final long endIndex; // Exclusive end index of the segment
    private final long limit; // Overall upper limit (N)
    private final List<Long> basePrimes; // Primes up to sqrt(limit) used for sieving

    public SieveSegmentTask(BitSet isPrime, long startIndex, long endIndex, long limit, List<Long> basePrimes) {
      this.isPrime = isPrime;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
      this.limit = limit;
      this.basePrimes = basePrimes;
    }

    @Override
    public Void call() {
      long currentStartIndex = Math.max(2L, startIndex);
      long currentEndIndex = Math.min(limit + 1, endIndex);

      if (currentStartIndex >= currentEndIndex) {
        return null; // Invalid segment, nothing to sieve
      }

      // Mark 0 and 1 as not prime if they fall within the segment
      if (currentStartIndex <= 0 && currentEndIndex > 0) {
        isPrime.clear(0);
      }
      if (currentStartIndex <= 1 && currentEndIndex > 1) {
        isPrime.clear(1);
      }

      // Iterate through the base primes to mark multiples in the segment
      for (long p : basePrimes) {
        // If p*p exceeds the overall limit, or if the base prime is too large for this
        // segment,
        // we can stop sieving with this prime.
        if (p * p > limit) {
          break;
        }

        // Calculate the first multiple of 'p' that is within the current segment and >=
        // p*p.
        // Multiples smaller than p*p would have already been marked by smaller primes.
        long startMultiple = Math.max(p * p, ((currentStartIndex + p - 1) / p) * p);

        // Mark multiples of 'p' within the segment as composite (clear the bit)
        for (long i = startMultiple; i < currentEndIndex; i += p) {
          // BitSet.clear() sets the bit to false (non-prime)
          isPrime.clear((int) i); // Cast to int because BitSet indices are int
        }
      }
      return null; // Task completed
    }
  }

  // Main function to calculate primes up to a given limit concurrently
  public long calculatePrimes(long limit) {
    if (limit < 2) {
      return 0; // No primes less than 2
    }

    // BitSet is used for memory-efficient storage of boolean flags.
    // It automatically manages its size and is generally performant.
    // Initialize all bits as true (potential prime), then clear for non-primes.
    BitSet isPrime = new BitSet((int) (limit + 1));
    isPrime.set(0, (int) (limit + 1)); // Set all bits from 0 to limit to true initially

    isPrime.clear(0); // 0 is not prime
    isPrime.clear(1); // 1 is not prime

    // --- Step 1: Initial Sieve for base primes up to sqrt(limit) ---
    // These primes are found sequentially and will be used by worker threads.
    long sqrtLimit = (long) Math.sqrt(limit);
    List<Long> basePrimes = new ArrayList<>();

    // Perform a sequential sieve up to sqrtLimit to find base primes
    for (long p = 2; p * p <= sqrtLimit; p++) {
      if (isPrime.get((int) p)) { // If p is currently marked as prime
        for (long i = p * p; i <= sqrtLimit; i += p) {
          isPrime.clear((int) i); // Mark multiples as not prime
        }
      }
    }

    // Collect the base primes from the sieved portion up to sqrtLimit
    // These are the primes that will be used by parallel segments.
    for (long p = 2; p <= sqrtLimit; p++) {
      if (isPrime.get((int) p)) {
        basePrimes.add(p);
      }
    }

    // --- Step 2: Parallel Segmented Sieving ---
    // Determine the number of threads based on available CPU cores.
    int numThreads = Runtime.getRuntime().availableProcessors();
    if (numThreads == 0) {
      numThreads = 4; // Fallback if no processor info is available
    }
    System.out.println("Using " + numThreads + " threads for prime calculation.");

    // Create an ExecutorService to manage the thread pool.
    // ForkJoinPool is often a good choice for CPU-bound tasks like this.
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    List<Future<Void>> futures = new ArrayList<>(); // To hold results of submitted tasks

    long segmentSize = limit / numThreads; // Approximate size of each segment

    // Submit tasks for each segment to the ExecutorService
    for (int i = 0; i < numThreads; i++) {
      long start = i * segmentSize;
      long end = (i == numThreads - 1) ? (limit + 1) : (i + 1) * segmentSize;

      // Create and submit a new SieveSegmentTask for each segment
      Callable<Void> task = new SieveSegmentTask(isPrime, start, end, limit, basePrimes);
      futures.add(executor.submit(task));
    }

    // Wait for all tasks to complete
    for (Future<Void> future : futures) {
      try {
        future.get(); // Blocks until the task is done, retrieves null or throws exception
      } catch (Exception e) {
        System.err.println("Error in parallel sieve task: " + e.getMessage());
        // Handle or rethrow as appropriate for a production system
      }
    }

    // Shut down the executor service gracefully
    executor.shutdown();
    try {
      // Wait for all tasks to finish or timeout after a period
      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        System.err.println("Executor did not terminate in time. Some tasks might be incomplete.");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restore interrupt status
      System.err.println("Thread interrupted while waiting for executor termination.");
    }

    // --- Step 3: Count the primes ---
    // BitSet.cardinality() efficiently counts the number of set (true) bits.
    // Since 0 and 1 are cleared, this gives the correct prime count.
    // Note: For large limits, ensure the BitSet size fits in int.
    // For 10,000,000, it fits.
    return isPrime.cardinality();
  }

  // Main entry point of the Java program
  public static void main(String[] args) {
    System.out.println("Java Asynchronous Prime Number Calculator");
    long limit = 10_000_000; // The specified limit: 10 million (using underscore for readability)

    // Create an instance of the PrimeCalculator
    PrimeCalculator calculator = new PrimeCalculator();

    // Record the start time
    long startTime = System.nanoTime();

    // Calculate primes
    long count = calculator.calculatePrimes(limit);

    // Record the end time
    long endTime = System.nanoTime();
    // Calculate the duration in milliseconds
    long durationMillis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

    // Print the results
    System.out.println("\n--------------------------------------------");
    System.out.println("Calculation Complete.");
    System.out.println("Number of primes up to " + limit + ": " + count);
    System.out.println("Time taken: " + durationMillis + " milliseconds");
    System.out.println("--------------------------------------------");
  }
}