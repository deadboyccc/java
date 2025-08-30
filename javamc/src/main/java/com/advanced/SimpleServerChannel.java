package com.advanced;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleServerChannel {
  public static void main(String[] args) {
    try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
      serverChannel.socket().bind(new InetSocketAddress(5000));
      System.out.println("Sever is listening locally on port : " + serverChannel.socket().getLocalPort());

      while (true) {
        SocketChannel clientChannel = serverChannel.accept();
        System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = clientChannel;
        int readBytes = channel.read(buffer);
        if (readBytes > 0) {
          buffer.flip();
          channel.write(ByteBuffer.wrap("Echo from Sever".getBytes()));
          while (buffer.hasRemaining()) {
            channel.write(buffer);
          }
          buffer.clear();
        } else if (readBytes == -1) {
          System.out.printf("client %s disconnected%n", channel.socket().getRemoteSocketAddress());
          channel.close();
        }
      }

    } catch (IOException e) {
      System.out.println("Sever Exception: " + e.getMessage());
    }
  }

}
