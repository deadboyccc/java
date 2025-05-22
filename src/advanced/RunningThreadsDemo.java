package advanced;

public class RunningThreadsDemo {
    public static void main(String[] args) {
        System.out.println("Main Thread is Running!");
        try {
            System.out.println("Main Thread will pause for one second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("This Task should take 10 dots to run!");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    Thread.sleep(500);
                    System.out.println("A. State = " + Thread.currentThread().getState());
                } catch (Exception e) {
                    // TODO: handle exception
                    // fail safe
                    // e.printStackTrace();
                    System.out.println();
                    System.out.println("Thread Interrupted!");
                    System.out.println("A1. State = " + Thread.currentThread().getState());
                    return;
                }

            }
            System.out.println("Thread complete its work (printing dots)!");

        });
        System.out.println(thread.getName() + " Starting!");
        thread.start();
        long now = System.currentTimeMillis();
        while (thread.isAlive()) {
            System.out.println("Waiting for the thread to complete!");

            try {
                Thread.sleep(1000);
                System.out.println("B. State = " + Thread.currentThread().getState());
                if (System.currentTimeMillis() - now > 2000) {
                    thread.interrupt();
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("B1. State = " + Thread.currentThread().getState());
            }
        }
        System.out.println("C. State= " + thread.getState());

        // review 4. then do challenge 5

        // System.out.println("Main Thread continuing!");
        // try {
        // Thread.sleep(2000);
        // } catch (Exception e) {

        // // TODO: handle exception
        // e.printStackTrace();
        // }

        // // interrupting
        // thread.interrupt();

    }

}
