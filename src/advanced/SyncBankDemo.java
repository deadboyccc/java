package advanced;

public class SyncBankDemo {
    public static void main(String[] args) {
        SyncBankDemo companyAccount = new SyncBankDemo("Joe", 10000);
        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread3 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread4 = new Thread(() -> companyAccount.setName("New Joe"));

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        thread4.start();
        thread3.start();

        try {
            thread4.join();
            thread1.join();
            thread2.join();
            thread3.join();

        } catch (Exception e) {

            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println("Final balance = " + companyAccount.getBalance());

    }

    private String name;

    private double balance;
    private final Object lockName = new Object();
    private final Object lockBalance = new Object();

    public SyncBankDemo(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            System.out.println("shouldn't block here!");
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // more fine grained
        synchronized (lockBalance) {

            double origBalance = balance;
            balance += amount;

            System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)" +
                    ": NEW BALANCE = %.0f%n", origBalance, amount, balance);
            // no deadlock cuz same thread ( who has the lock ) is calling the other method
            // reentrant sync
            addPromoDollars(amount);
        }
    }

    private void addPromoDollars(double amount) {
        if (amount >= 1000) {
            synchronized (lockBalance) {
                System.out.println("promo");
                balance += 25;

            }

        }
    }

    public synchronized void withdraw(double amount) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double origBalance = balance;
        if (amount <= balance) {

            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAW (%.0f)" +
                    ": NEW BALANCE = %.0f%n", origBalance, amount, balance);
        } else {
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAW (%.0f)" +
                    ": INSUFFIENT FUNDS!", origBalance, amount);

        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        synchronized (lockName) {

            this.name = name;
            System.out.println("updated name: " + this.name);
        }

    }

}
