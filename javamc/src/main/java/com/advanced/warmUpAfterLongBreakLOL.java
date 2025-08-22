package com.advanced;

import java.util.HashMap;

public class warmUpAfterLongBreakLOL {
  public static void main(String[] args) {
    System.out.println("Heyyyyyyyyyyyyyyyyo X");
    HashMap<Integer, String> test1 = new HashMap<>();
    test1.put(1, "ONE");
    test1.put(2, "two");
    test1.forEach((i, s) -> {
      System.out.println(i + "-> " + s);
    });

  }

}
