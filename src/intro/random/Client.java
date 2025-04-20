package intro.random;

import java.net.*;
import java.io.*;

public class Client {
    public void go() {
        try {
            // 533
            Socket chatsoSocket = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(chatsoSocket.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String string = reader.readLine();
            System.out.println(string);
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client clinet = new Client();
        clinet.go();
    }
}
