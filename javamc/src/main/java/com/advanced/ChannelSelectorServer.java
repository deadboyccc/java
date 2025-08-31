package com.advanced;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChannelSelectorServer {
  // event driven
  public static void main(String[] args) {
    try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

      // server
      serverChannel.bind(new InetSocketAddress(5000));
      serverChannel.configureBlocking(false);

      // selector
      Selector selector = Selector.open();

      // registering
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);

      // Main Loop -> constantly polling for events
      while (true) {
        selector.select();
        Set<SelectionKey> selectedKey = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectedKey.iterator();
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          iterator.remove();
          // ops
          if (key.isAcceptable()) {
            SocketChannel clientChannel = serverChannel.accept();
          }

        }

      }

    } catch (IOException io) {
      System.out.println(io.getLocalizedMessage());
    }

  }

  private static void echoData(SelectionKey key) throws IOException {
    SocketChannel clientChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    int bytesRead = clientChannel.read(buffer);
    if (bytesRead > 0) {
      buffer.flip();
      byte[] data = new byte[buffer.remaining()];
      buffer.get(data);
      String message = "Echo: " + new String(data);
      clientChannel.write(ByteBuffer.wrap(message.getBytes()));
    } else if (bytesRead == -1) {
      System.out.println("Client Disconnected: " + clientChannel.getRemoteAddress());
      key.cancel();
      clientChannel.close();
    }
  }
}