package advanced;

import java.text.Normalizer.Form;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SchedulingTasksDemo {

    public static void main(String[] args) {
        // Set the default locale to en/US before creating the formatter
        java.util.Locale.setDefault(java.util.Locale.US);
        var dtf = DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM,
                FormatStyle.LONG);

        Callable<ZonedDateTime> waitThenDoIt = () -> {
            ZonedDateTime zdt = null;
            try {
                TimeUnit.SECONDS.sleep(2);
                zdt = ZonedDateTime.now();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return zdt;
        };
        var threadPool = Executors.newFixedThreadPool(2);
        List<Callable<ZonedDateTime>> list = Collections.nCopies(4, waitThenDoIt);

        try {
            System.out.println("----> " + ZonedDateTime.now().format(dtf));
            var futures = threadPool.invokeAll(list);
            // and holds until they all finish
            for (Future<ZonedDateTime> future : futures) {
                System.out.println(future.get(3, TimeUnit.SECONDS).format(dtf));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }

        System.out.println("-----> " + ZonedDateTime.now().format(dtf));
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        ScheduledFuture<?> tasks = executor.scheduleWithFixedDelay(() -> {
            System.out.println(ZonedDateTime.now().format(dtf));
        }, 2, 2, TimeUnit.SECONDS);

        long time = System.currentTimeMillis();
        while (!tasks.isDone()) {
            try {

                TimeUnit.SECONDS.sleep(2);
                if ((System.currentTimeMillis() - time) / 1000 > 9) {
                    tasks.cancel(true);

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        executor.shutdown();
    }

}
