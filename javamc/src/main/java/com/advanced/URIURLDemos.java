package com.advanced;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URIURLDemos {
  public static void main(String[] args) {
    try {
      URL jsonPlaceholder = new URL("https://jsonplaceholder.typicode.com/todos/1");
      var stream = jsonPlaceholder.openConnection().getInputStream();
      InputStreamReader isr = new InputStreamReader(stream);
      int data;
      while ((data = isr.read()) != -1) {
        System.out.print((char) data);
      }
      isr.close();
      stream.close();
    } catch (IOException e) {
      System.out.println(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }
}
