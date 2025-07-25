package com.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleSever {
  public static void main(String[] args) {
    var excutorService = Executors.newCachedThreadPool();
    try (ServerSocket serverSocket = new ServerSocket(5000)) {
      System.out
          .println("ServerSocket started at " + serverSocket.getLocalSocketAddress());
      while (true) {
        Socket socket = serverSocket.accept();
        socket.setSoTimeout(900_000);

        System.out.println("Server accepted a client connection");

        excutorService.submit(() -> handleClientRequest(socket));
      }
    } catch (IOException e) {
      System.out.println("Server error: " + e.getMessage());
    }

  }

  public static void handleClientRequest(Socket socket) {
    try (
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
      while (true) {
        String echoString = input.readLine();
        System.out.println("Server got request data : " + echoString);
        if (echoString == null || echoString.equals("exit")) {
          break;
        }
        output.println("Echo from Server: " + echoString);
      }
    } catch (IOException e) {
      System.out.println("Error handling client: " + e.getMessage());
    }
  }
}
