package com.advanced;

import java.io.*;
import java.net.*;
import java.util.logging.*;

// UDP audio streaming classes
public class videoStreamUDP {

  // UDP server to stream audio file
  public static class AudioStreamUDPServer {
    private static final int PORT = 5000;
    private static final int PACKET_SIZE = 4096;
    private static final Logger logger = Logger.getLogger(AudioStreamUDPServer.class.getName());

    // Start server and stream audio to client
    public void start(String audioFilePath) {
      try (DatagramSocket socket = new DatagramSocket();
          FileInputStream fis = new FileInputStream(audioFilePath)) {

        logger.info("AudioStreamUDPServer started on port " + PORT);
        byte[] buffer = new byte[PACKET_SIZE];
        InetAddress clientAddress = null;
        int clientPort = -1;

        // Wait for client connection
        DatagramPacket initPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(initPacket);
        clientAddress = initPacket.getAddress();
        clientPort = initPacket.getPort();
        logger.info("Client connected: " + clientAddress + ":" + clientPort);

        int bytesRead;
        // Send audio data in packets
        while ((bytesRead = fis.read(buffer)) != -1) {
          DatagramPacket packet = new DatagramPacket(buffer, bytesRead, clientAddress, clientPort);
          socket.send(packet);
          logger.fine("Sent packet of size: " + bytesRead);
          Thread.sleep(10); // Throttle sending
        }
        logger.info("Audio streaming finished.");
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Error in AudioStreamUDPServer", e);
      }
    }
  }

  // UDP client to receive audio stream
  public static class VideoStreamUDPClient {
    private static final int SERVER_PORT = 5000;
    private static final int PACKET_SIZE = 4096;
    private static final Logger logger = Logger.getLogger(VideoStreamUDPClient.class.getName());

    // Connect to server and save received audio
    public void connectAndReceive(String serverIp, String outputFilePath) {
      try (DatagramSocket socket = new DatagramSocket();
          FileOutputStream fos = new FileOutputStream(outputFilePath)) {

        // Send initial packet to server
        byte[] init = new byte[1];
        DatagramPacket initPacket = new DatagramPacket(init, init.length, InetAddress.getByName(serverIp), SERVER_PORT);
        socket.send(initPacket);
        logger.info("Sent initial packet to server.");

        byte[] buffer = new byte[PACKET_SIZE];
        // Receive audio data packets
        while (true) {
          DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
          socket.receive(packet);
          if (packet.getLength() == 0)
            break;
          fos.write(packet.getData(), 0, packet.getLength());
          logger.fine("Received packet of size: " + packet.getLength());
        }
        logger.info("Audio stream received and saved to " + outputFilePath);
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Error in VideoStreamUDPClient", e);
      }
    }
  }
}
