package advanced;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

class MessageRepo {
    private String message;
    private boolean hasMessage = false;

    private final Lock lock = new ReentrantLock();

    public String read() {
        if (lock.tryLock()) {

            try {

                while (!hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                        throw (e);
                    }
                }
                hasMessage = false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Reader blocked "+lock);
            hasMessage = false;
        }
        return message;
    }

    public void write(String message) {
        if (lock.tryLock()) {
            try {
                while (hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        } else {

            System.out.println("writer blocked ");
            hasMessage = true;
        }
        this.message = message;
    }
}

class MessageWriter implements Runnable {
    private MessageRepo outgoingMessage;
    private final String text = """
            Joe Biden vs Humpty Dumpty 0
            Joe Biden vs Humpty Dumpty 1
            Joe Biden vs Humpty Dumpty 2
            Joe Biden vs Humpty Dumpty 3
            Joe Biden vs Humpty Dumpty 5
            Joe Biden vs Humpty Dumpty 6
            Joe Biden vs Humpty Dumpty 7
            Joe Biden vs Humpty Dumpty 8
            Joe Biden vs Humpty Dumpty 9
            """;

    public MessageWriter(MessageRepo outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text.strip().split("\\R");
        for (String line : lines) {
            outgoingMessage.write(line);
            try {
                Thread.sleep(random.nextInt(100, 200));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        outgoingMessage.write("Finished");
    }
}

class MessageReader implements Runnable {
    private MessageRepo incomingMessage;

    public MessageReader(MessageRepo incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String latestMessage;
        do {
            try {
                Thread.sleep(random.nextInt(100, 200));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            latestMessage = incomingMessage.read();
            System.out.println(latestMessage);
        } while (!"Finished".equals(latestMessage));
    }
}

public class JavaUtilsConcurrentLocksDemo {
    public static void main(String[] args) {
        MessageRepo messageRepo = new MessageRepo();
        Thread reader = new Thread(new MessageReader(messageRepo), "ReaderThread");
        Thread writer = new Thread(new MessageWriter(messageRepo), "WriterThread");

        writer.setUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("Writer thread had an exception");
            if (reader.isAlive()) {
                System.out.println("Terminating Reader");
                reader.interrupt();
            }
        });

        reader.setUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("Reader thread had an exception");
            if (writer.isAlive()) {
                System.out.println("Terminating Writer");
                writer.interrupt();
            }
        });

        reader.start();
        writer.start();
    }
}
