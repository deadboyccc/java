public class threadTester {
    public static void main(String[] args) {
        Runnable job = new MyRunnable();
        Thread t = new Thread(job);
        Thread t2 = new Thread(job);
        t.start();
        t2.start();
    }
    
}
