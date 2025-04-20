import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Test implements Runnable {

    @Override
    public void run() {
        System.out.println("hello world ");

    }

}

public class Server {

    String[] adviceList = new String[] { "Take smaller bites",
            "Go for the tight jeans. No they do NOT make you look fat.", "One word: inappropriate",
            "Just for today, be honest. Tell your boss what you *really* think",
            "You might want to rethink that haircut." };

    public void go() {


        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {

                Socket sock = serverSocket.accept();
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                // serverSocket.close();
                writer.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Test());
        thread1.start();
        
        Server serverSocket = new Server();
        serverSocket.go();
    }
}
