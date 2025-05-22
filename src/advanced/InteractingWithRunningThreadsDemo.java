package advanced;

public class InteractingWithRunningThreadsDemo {
    public static void main(String[] args) {
        // Starting Main Thread
        System.out.println("Main Threading Running!");

        // Sleeping Main Thread for 1s
        try {

            System.out.println("Main Thread paused for 1 second");
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + " should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops! Thread " + tname + " wasInterrupted");
                    Thread.currentThread().interrupt();
                    return;
                    // TODO: handle exception
                }

            }
            System.out.println("\nThread " + tname + " finsihed its work successfully");

        });
        Thread installThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {

                    Thread.sleep(250);
                    System.out.println("Installation step " + i + " is completed");
                }
            } catch (Exception e) {

                e.printStackTrace();
                // TODO: handle exception
            }
        }, "InstallThread");
        // Continuing Main Thread

        Thread threadMonitor = new Thread(() -> {

            long now = System.currentTimeMillis();
            while (thread.isAlive()) {
                try {

                    Thread.sleep(1000);
                    if (System.currentTimeMillis() - now > 2000) {
                        thread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }

            }
        });
        System.out.println(thread.getName() + " Starting!");
        thread.start();
        threadMonitor.start();
        try {
        thread.join();
        } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        if (!thread.isInterrupted()) {
            installThread.start();
        } else {
            System.out.println(installThread.getName() + " thread can't run! cuz download thread was interrupted");
        }
    }

}