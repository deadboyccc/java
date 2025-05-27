package advanced;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CachedThreadPoolExecutorServiceDemo {
    public static void main(String[] args) {
        System.out.println("s");
        List<Callable<Integer>> tasks = List.of(()->sum(2),()->sum(3),()->sum(4));
        var service = Executors.newCachedThreadPool();
        try {
            List<Future<Integer>> li = service.invokeAll(tasks);
            for (Future<Integer> future : li) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
    public static void notmain2(String[] args) {
        var service = Executors.newCachedThreadPool();
        try {

            Future<Integer> fi = service.submit(() -> 
                // for (int i = 0; i < 3; i++) {
                //     System.out.println(ThreadColor.ANSI_GREEN.color() + " " + Thread.currentThread().getName());

                // }
                sum(3)
            );
            service.submit(() -> {
                // for (int i = 0; i < 3; i++) {
                //     System.out.println(ThreadColor.ANSI_BLUE.color() + " " + Thread.currentThread().getName());
                // }
                sum(4);
            });
            service.execute(() -> {
                // for (int i = 0; i < 3; i++) {
                //     System.out.println(ThreadColor.ANSI_RED.color() + " " + Thread.currentThread().getName());
                // }
                sum(8);
            });
            service.execute(() -> {
                for (int i = 0; i < 3; i++) {
                    System.out.println(ThreadColor.ANSI_YELLOW.color() + " " + Thread.currentThread().getName());
                }
            });
            System.out.println(fi.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
    private static int sum(int end){
        int sum=0;
        for (int i = 0; i < end; i++) {
            sum+=i;
        }
        return sum;
    }

}
