package com.advanced;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
  public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 5000);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      String requeString;
      String responseString;

      do {
        System.out.println("Enter a string to be echoed (sent to the server): ");
        requeString = scanner.nextLine();
        output.println(requeString);
        if (!requeString.equals("exit")) {
          responseString = input.readLine();
          System.out.println(responseString);

        }
      } while (!requeString.equals("exit"));
    } catch (Exception e) {
      System.out.println("Client Error: " + e.getMessage());
    } finally {
      System.out.println("Client Disconnected");

    }
  }

}
