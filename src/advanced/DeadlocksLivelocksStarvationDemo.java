package advanced;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DeadlocksLivelocksStarvationDemo {
    public static void main(String[] args) {
        File resourceA = new File("inputData.csv");
        File resourceB = new File("outputData.json");

        Thread threadA = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock resourceA (CSV)");
            synchronized (resourceA) {
                System.out.println(threadName + " has lock on resourceA (CSV)");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " NEXT attempting to lock resouceB (JSON)," +
                        " still holding a lock on resourceA (CSV)");
                synchronized (resourceB) {

                    System.out.println(threadName + " has lock on resourceB (JSON)");
                }
                System.out.println(threadName + " has released resouceB (JSON)");
            }
            System.out.println(threadName + " has released resourceA (CSV)");
        }, "THREAD-A");

        Thread threadC = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock resourceB (JSON)");
            synchronized (resourceB) {
                System.out.println(threadName + " has lock on resourceB (JSON)");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " NEXT attempting to lock resourceA (CSV)," +
                        " still holding a lock on resourceB (JSON)");
                synchronized (resourceA) {
                    System.out.println(threadName + " has lock on resourceA (CSV)");
                }
                System.out.println(threadName + " has released resourceA (CSV)");
            }
            System.out.println(threadName + " has released resourceB (JSON)");
        }, "THREAD-C");
        Thread threadB = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock resourceA (CSV)");
            synchronized (resourceA) {
                System.out.println(threadName + " has lock on resourceA (CSV)");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " NEXT attempting to lock resouceB (JSON)," +
                        " still holding a lock on resourceA (CSV)");
                synchronized (resourceB) {

                    System.out.println(threadName + " has lock on resourceB (JSON)");
                }
                System.out.println(threadName + " has released resouceB (JSON)");
            }
            System.out.println(threadName + " has released resourceA (CSV)");
        }, "THREAD-B");

        // Simple Deadlock demo
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
