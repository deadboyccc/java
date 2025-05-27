package advanced;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ColorThreadFactory implements ThreadFactory {
    private String threadName;
    private int colorValue = 1;

    public ColorThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        String name = threadName;
        if (name == null) {
            name = ThreadColor.values()[colorValue].name();

        } else if (++colorValue > (ThreadColor.values().length - 1)) {
            colorValue = 1;

        }
        thread.setName(name);
        return thread;
    }

}

public class FixedThreadPoolClassDemo {
    public static void main(String[] args) {
        int count = 3;
        var executorService = Executors.newFixedThreadPool(count, new ColorThreadFactory());
        for (int i = 0; i < count; i++) {
            executorService.execute(FixedThreadPoolClassDemo::countDown);
        }
        executorService.shutdown();
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
