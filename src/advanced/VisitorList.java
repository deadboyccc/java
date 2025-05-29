package advanced;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VisitorList {
    private static final ArrayBlockingQueue<Person> newVisitors = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        Runnable producer = () -> {
            Person visitor = new Person();
            System.out.println("-> Adding " + visitor);
            boolean queued = false;

            try {
                queued = newVisitors.offer(visitor, 5, TimeUnit.SECONDS);

            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
            }

            if (queued) {
                System.out.println(newVisitors);
            } else {
                System.out.println("-> queue is full");
                System.out.println("-> drainig queue and writing data to file");
                List<Person> temp = new ArrayList<>();
                newVisitors.drainTo(temp);
                List<String> lines = new ArrayList<>();
                temp.forEach((customer) -> lines.add(customer.toString()));
                lines.add(visitor.toString());
                try {
                    Files.write(Path.of("DrainedQueue.txt"), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }

        };
        try (ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor()) {
            producerExecutor.scheduleWithFixedDelay(producer, 0, 1, TimeUnit.SECONDS);
            waitForProducerShutdown(producerExecutor);
        }

    }

    private static void waitForProducerShutdown(ScheduledExecutorService producerExecutor) {
        while (true) {
            try {
                if (!producerExecutor.awaitTermination(20, TimeUnit.SECONDS)) {
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
