package intro;

import java.util.ArrayList;
import java.util.Arrays;

public class Array {
  public static void main(String[] args) {

    ArrayList<Integer> test = new ArrayList<Integer>();
    // fixed size
    int[] intArr = new int[10];
    intArr[5] = 50;
    System.out.println(intArr.getClass().getSimpleName());
    System.out.println(intArr[1]);
    System.out.println(Arrays.toString(intArr));

  }

}
