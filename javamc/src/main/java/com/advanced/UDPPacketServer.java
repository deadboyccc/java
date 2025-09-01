package com.advanced;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPPacketServer {
  private static final int PORT = 5000;
  private static final int PACKET_SIZE = 1024;

  public static void main(String[] args) {
    try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
      // buffer to get data from the client
      byte[] buffer = new byte[PACKET_SIZE];
      System.out.println("waiting for a client connection : ");
      // transferring the buffer to the datagram packet
      DatagramPacket cleintPacket = new DatagramPacket(buffer, buffer.length);
      serverSocket.receive(cleintPacket);
      // getting the audio file name requested
      String audioFileName = new String(buffer, 0, cleintPacket.getLength());
      System.out.println("Client requested: " + audioFileName);

    } catch (IOException io) {
      System.out.println(io.getMessage());
    }

  }

}
