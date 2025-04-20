import java.io.IOException;
import java.net.*;
public class Networking {
    public static void main(String[] args) {
        try {
            
            Socket chatSocket = new Socket("127.0.0.1", 8081);
            chatSocket.sendUrgentData(500);
            chatSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
