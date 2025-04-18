package intro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Array {

  public static void main(String[] args) {

    int[][] array2 = new int[3][4];
    for (int[] outer : array2) {
      System.out.println(Arrays.toString(outer));

    }
    for (int i = 0; i < array2.length; i++) {
      // auto infer
      var innner = array2[i];
      for (int j = 0; j < innner.length; j++) {
        array2[i][j] = (i * 10) + (j + 1);
        // System.out.print(array2[i][j] + " ");
      }
      // System.err.println();
    }
    for (var outer : array2) {
      for (var element : outer) {
        System.out.print(element + "_");
      }
      System.err.println();
    }
    System.out.println("_".repeat(20));
    System.out.println(Arrays.deepToString(array2));
    int[] firstArr = getRAndomArray(10);
    System.out.println(Arrays.toString(firstArr));
    Arrays.sort(firstArr);
    System.out.println(Arrays.toString(firstArr));
    int[] secondArr = new int[10];
    System.out.println(Arrays.toString((secondArr)));
    Arrays.fill(secondArr, 5);
    System.out.println(Arrays.toString((secondArr)));
    int[] thirdArr = getRAndomArray(10);
    int[] fourthArr = Arrays.copyOf(thirdArr, thirdArr.length);
    System.out.println(Arrays.toString(fourthArr));
    Arrays.sort(fourthArr);
    System.out.println(Arrays.toString(thirdArr));
    System.out.println(Arrays.toString(fourthArr));
    String[] sArray = { "joe", "moh", "mark", "sh", "oo", "qq" };
    Arrays.sort(sArray);
    System.out.println(Arrays.toString(sArray));
    // interval vs seq searching
    if (Arrays.binarySearch(sArray, "mark") >= 0) {
      System.out.println("mark found!");

    }
    // equality
    int[] s1 = new int[] { 1, 2, 3, 4, 5 };
    int[] s2 = new int[] { 1, 2, 3, 4, 5 };
    if (Arrays.equals(s1, s2)) {
      System.out.println("equal");
    } else {
      System.out.println("not equal");
    }

  }

  private static void test1() {
    ArrayList<Integer> test = new ArrayList<Integer>();
    test.add(10);
    System.out.println(test.stream().count());
    // fixed size
    int[] intArr = new int[10];
    intArr[5] = 50;
    System.out.println(intArr.getClass().getSimpleName());
    System.out.println(intArr[1]);
    System.out.println(Arrays.toString(intArr));
  }

  private static int[] getRAndomArray(int len) {
    Random random = new Random();
    int[] arr = new int[len];
    for (int i = 0; i < len; i++) {
      arr[i] = random.nextInt(100);

    }

    return arr;

  }
}
