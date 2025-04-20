// RunThreads.java

public class RunThreads implements Runnable {

    public static void main(String[] args) {
        // Create a single Runnable instance
        RunThreads runner = new RunThreads();

        // Create two Threads, both using the same Runnable
        Thread alpha = new Thread(runner);
        Thread beta  = new Thread(runner);

        // Name the threads
        alpha.setName("Alpha thread");
        beta.setName("Beta thread");

        // Start both threads (invokes run() in separate threads) :contentReference[oaicite:0]{index=0}
        alpha.start();
        beta.start();
    }

    @Override
    public void run() {
        // Each thread will execute this method independently
        for (int i = 0; i < 25; i++) {
            // Get and print the current thread’s name :contentReference[oaicite:1]{index=1}
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " is running (iteration " + i + ")");
        }
    }
}

