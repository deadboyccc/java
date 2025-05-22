package advanced;

import java.util.concurrent.TimeUnit;

public class MultiThreadingAndMemoryDemo {
    public static void main(String[] args) {
        // single instance
        ThreadStopWatch stopWatch = new ThreadStopWatch(TimeUnit.SECONDS);

        Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> stopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();

    }

}

class ThreadStopWatch {
    // shared
    private int i;
    private TimeUnit timeUnit;

    public ThreadStopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown() {
        countDown(5);
    }

    // non-sync
    public void countDown(int unitcount) {
        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException e) {
            // If threadName does not match any enum, keep ANSI_RESET
        }
        String color = threadColor.color();
        for (i = unitcount; i > 0; i--) {
            try {
                timeUnit.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }

}