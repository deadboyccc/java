package advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VisitorList {
    // Thread-safe list of known visitors
    private static final CopyOnWriteArrayList<Person> masterList = new CopyOnWriteArrayList<>();

    // Bounded queue for incoming visitors
    private static final ArrayBlockingQueue<Person> newVisitors = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        preloadMasterList();

        ScheduledExecutorService consumerPool = Executors.newScheduledThreadPool(3);
        ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor();

        Runnable producer = () -> {
            Person visitor = new Person();
            System.out.println("-> Queueing " + visitor);
            boolean queued;
            try {
                queued = newVisitors.offer(visitor, 5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!queued) {
                System.out.println("-> Queue is full. Draining to file...");
                drainQueueToFile(visitor);
            }
        };

        Runnable consumer = () -> {
            String threadName = Thread.currentThread().getName();
            Person visitor = newVisitors.poll();
            if (visitor != null) {
                System.out.println(threadName + " processed: " + visitor);
                if (!masterList.contains(visitor)) {
                    masterList.add(visitor);
                    System.out.println("--> New visitor gets coupon: " + visitor);
                }
            }
        };

        // Start consumers
        for (int i = 0; i < 3; i++) {
            consumerPool.scheduleAtFixedRate(consumer, 0, 3, TimeUnit.SECONDS);
        }

        // Start producer
        producerExecutor.scheduleWithFixedDelay(producer, 0, 1, TimeUnit.SECONDS);

        // Let simulation run for 30 seconds, then shut down
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        shutdownExecutors(producerExecutor, consumerPool);
    }

    private static void preloadMasterList() {
        // Generate 2500 unique people (assumes proper equals/hashCode on Person)
        Set<Person> uniquePeople = new HashSet<>();
        while (uniquePeople.size() < 2500) {
            uniquePeople.add(new Person());
        }
        masterList.addAll(uniquePeople);
    }

    private static void drainQueueToFile(Person extraPerson) {
        List<Person> drained = new ArrayList<>();
        newVisitors.drainTo(drained);
        drained.add(extraPerson); // Include the rejected visitor

        List<String> lines = drained.stream()
                .map(Person::toString)
                .collect(Collectors.toList());

        try {
            Files.write(Path.of("DrainedQueue.txt"), lines,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void shutdownExecutors(ExecutorService... executors) {
        for (ExecutorService executor : executors) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
