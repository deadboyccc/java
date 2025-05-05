package advanced;

import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class ConncurrencyDemo1 {
    public static void main(String[] args) {
        var currentThread = Thread.currentThread();

        System.out.println(currentThread);

        System.out.println("______".repeat(20));
        printThreadState(currentThread);

        currentThread.setName("MainThread");
        currentThread.setPriority(Thread.MAX_PRIORITY);
        printThreadState(currentThread);
        Thread t1 = new CustomThread();

        // sync = run()
        // async = start()

        t1.start();

        // Runnable
        Runnable runnable = () -> {
            for (int i = 0; i < 8; i++) {
                System.out.print(" 2 ");

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        
        };
        Thread t2 = new Thread(runnable);
        t2.start();

        for (int i = 0; i < 3; i++) {
            System.out.print(" 0 ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }


        // manipulation & communication of threads
        // Sleep() 

        //
        //
        //
        // try {
        // t1.join();
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    public static void printThreadState(Thread thread) {
        System.out.println("--------------------");
        System.out.println("Thread ID: " + thread.getId());
        System.out.println("Thread Name: " + thread.getName());
        System.out.println("Thread Priority: " + thread.getPriority());
        System.out.println("Thread State: " + thread.getState());
        System.out.println("Thread Group: " + thread.getThreadGroup());
        System.out.println("Thread Is Alive: " + thread.isAlive());
        System.out.println("--------------------");
    }

}
