package com.advanced;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPStreamingDemo {

  // Simple demo to send and receive video stream packets using UDP
  public static void main(String[] args) throws Exception {
    int port = 5000;
    String mode = args.length > 0 ? args[0] : "send";

    if (mode.equalsIgnoreCase("send")) {
      // Sender: simulate sending video frames
      DatagramSocket socket = new DatagramSocket();
      InetAddress address = InetAddress.getByName("localhost");
      for (int i = 0; i < 10; i++) {
        byte[] frame = ("Frame " + i).getBytes();
        DatagramPacket packet = new DatagramPacket(frame, frame.length, address, port);
        socket.send(packet);
        System.out.println("Sent: Frame " + i);
        Thread.sleep(100); // simulate frame rate
      }
      socket.close();
    } else if (mode.equalsIgnoreCase("audio-server")) {
      // Demo for UDP audio streaming using videoStreamUDP classes
      // Start audio streaming server
      String audioFilePath = "sample.wav"; // Path to your audio file
      videoStreamUDP.AudioStreamUDPServer server = new videoStreamUDP.AudioStreamUDPServer();
      server.start(audioFilePath);
    } else if (mode.equalsIgnoreCase("audio-client")) {
      // Start audio streaming client
      String serverIp = "localhost";
      String outputFilePath = "received.wav"; // Output file for received audio
      videoStreamUDP.VideoStreamUDPClient client = new videoStreamUDP.VideoStreamUDPClient();
      client.connectAndReceive(serverIp, outputFilePath);
    } else {
      // Receiver: receive video frames
      DatagramSocket socket = new DatagramSocket(port);
      byte[] buffer = new byte[1024];
      for (int i = 0; i < 10; i++) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String frame = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received: " + frame);
      }
      socket.close();
    }
  }
}
