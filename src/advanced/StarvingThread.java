package advanced;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class StarvingThread {
    private static final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {

        Callable<Boolean> thread = () -> {
            String threadName = Thread.currentThread().getName();
            String digits = threadName.replaceAll("\\D+", "");
            int threadNo = digits.isEmpty() ? 0 : Integer.parseInt(digits);
            boolean keepGoing = true;
            while (keepGoing) {
                lock.lock();
                try {
                    System.out.printf("%d has the lock.%n", threadNo);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.printf("Shutting down %d%n", threadNo);
                    Thread.currentThread().interrupt();
                    return false;
                } finally {
                    lock.unlock();
                }

            }
            return true;
        };
        var executor = Executors.newFixedThreadPool(3);
        try {
            var futures = executor.invokeAll(
                    List.of(thread, thread, thread), 10, TimeUnit.SECONDS);
            for (var future : futures) {
                try {
                    System.out.println("Thread completed: " + future.get());
                } catch (Exception e) {
                    System.out.println("Exception in thread: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        executor.shutdownNow();

    }

}
