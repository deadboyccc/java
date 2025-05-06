package advanced;

public class ThreadChallengeDemo {
    public static void main(String[] args) {
        Thread t1 = new SubThread();

        Thread t2 = new Thread(() -> {
            for (int i = 1; i < 7; i += 2) {
                try {
                    Thread.sleep(1000);
                    System.out.print("Odd Thread: " + i + " ");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Thread.currentThread().interrupt();
                    System.out.println("t2 Interrupted!");
                    break;
                    // e.printStackTrace();
                }

            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(1000);
            t1.interrupt();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
            t2.interrupt();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static class SubThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 7; i += 2) {
                try {
                    this.sleep(1000);
                    System.out.print("Even Thread: " + i + " ");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Thread.currentThread().interrupt();
                    System.out.println("t1 Interrupted!");
                    break;
                    // e.printStackTrace();
                }

            }
        }

    }

}
