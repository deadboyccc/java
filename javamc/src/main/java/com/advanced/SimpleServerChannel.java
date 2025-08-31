package com.advanced;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SimpleServerChannel {
  public static void main(String[] args) {
    try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
      serverChannel.socket().bind(new InetSocketAddress(5000));
      serverChannel.configureBlocking(false);

      List<SocketChannel> clientChannels = new ArrayList<>();
      System.out.println("Sever is listening locally on port : " + serverChannel.socket().getLocalPort());

      while (true) {

        SocketChannel clientChannel = serverChannel.accept();

        if (clientChannel != null) {
          clientChannel.configureBlocking(false);
          clientChannels.add(clientChannel);
          System.out.printf("Client %s connected%n", clientChannel.socket().getRemoteSocketAddress());
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < clientChannels.size(); i++) {

          SocketChannel channel = clientChannels.get(i);
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
            clientChannels.remove(i);
          }
        }
      }

    } catch (IOException e) {
      System.out.println("Sever Exception: " + e.getMessage());
    }
  }

}
