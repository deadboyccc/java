package advanced;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class ColorThreadFactory implements ThreadFactory {
    private String threadName;

    public ColorThreadFactory(ThreadColor threadColor) {
        threadName = threadColor.name();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }

}

public class ExecutorServiceClassesDemos {
    public static void main(String[] args) {
        System.out.println("_".repeat(40));
        var blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(ExecutorServiceClassesDemos::countDown);
        blueExecutor.shutdown();
        Boolean isDone = false;
        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isDone) {
            System.out.println("_".repeat(40));
            var yellowExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
            yellowExecutor.execute(ExecutorServiceClassesDemos::countDown);
            yellowExecutor.shutdown();
            try {
                isDone = yellowExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isDone) {

                System.out.println("_".repeat(40));
                var redExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_RED));
                redExecutor.execute(ExecutorServiceClassesDemos::countDown);
                redExecutor.shutdown();
                try {

                    isDone = redExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isDone) {
                    System.out.println("Done");
                }
            }

        }

    }

    public static void notMain(String[] args) {
        Thread blue = new Thread(ExecutorServiceClassesDemos::countDown, ThreadColor.ANSI_BLUE.name());
        Thread yellow = new Thread(ExecutorServiceClassesDemos::countDown, ThreadColor.ANSI_YELLOW.name());
        Thread red = new Thread(ExecutorServiceClassesDemos::countDown, ThreadColor.ANSI_RED.name());

        blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            red.start();

        }
        try {
            red.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void countDown() {
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName.toUpperCase());

        } catch (IllegalArgumentException e) {

        }
        String color = threadColor.color();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + threadName.replace("ANSI_", "") + " " + i);

        }

    }
}
