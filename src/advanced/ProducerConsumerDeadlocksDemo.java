package advanced;

import java.util.Random;

//shared
class MessageRepo {
    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {
        while (!hasMessage) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        hasMessage = false;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while (hasMessage) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        hasMessage = true;
        this.message = message;
        notifyAll();

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
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            outgoingMessage.write(lines[i]);
            try {

                Thread.sleep(random.nextInt(100, 30));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        outgoingMessage.write("Finished");

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
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
        String latestMessage = "";
        do {

            try {
                Thread.sleep(random.nextInt(500, 2000));

            } catch (Exception e) {
                e.printStackTrace();
            }
            latestMessage = incomingMessage.read();
            System.out.println(latestMessage);
        } while (!latestMessage.equals("Finished"));
    }

}

public class ProducerConsumerDeadlocksDemo {
    public static void main(String[] args) {

        MessageRepo messageRepo = new MessageRepo();

        Thread reader = new Thread(new MessageReader(messageRepo));
        Thread writer = new Thread(new MessageWriter(messageRepo));

        reader.start();
        writer.start();
    }

}
