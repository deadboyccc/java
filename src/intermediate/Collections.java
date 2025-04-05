package intermediate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Collections {
  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {

      DaysOfTheWeek test1 = getRandomDay();
      System.out.println(test1);
    }
  }

  private static DaysOfTheWeek getRandomDay() {
    int index = new Random().nextInt(7);
    var daysArr = DaysOfTheWeek.values();
    return daysArr[index];
  }

  private static void boxingUnboxing() {
    // Factory pattern is always avail on wrapper classes to box primitives
    Integer boxedInt = Integer.valueOf(10);
    // depcrecated way
    // Integer depBoxedInt = new Integer(10);

    // autoboxing || prefer
    Integer autoBoxedInt = 15;
    System.out.println(boxedInt);
    System.out.println(autoBoxedInt);

    // manual unboxing ( not pref )
    int primInt = autoBoxedInt.intValue();
    int AutoPrimInt = autoBoxedInt;
  }

  private static void ListsAndIterators() {
    // var should be the norm
    var objectList = new ArrayList<GrosseryItem>();
    objectList.add(new GrosseryItem("Milk"));
    // <> diamond operator :
    objectList.add(new GrosseryItem("Butter"));
    objectList.add(new GrosseryItem("Yogurt"));
    ArrayList<Integer> intList = new ArrayList<>(List.of(10, 11, 187, 1, 2, 8));
    intList.sort(Comparator.reverseOrder());
    // :3
    System.out.println(Arrays.toString(intList.toArray()));
    var intLinkedList = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5));
    Iterator<Integer> it = intLinkedList.iterator(); // limited compared to ListIterator

    while (it.hasNext()) {
      System.out.println(it.next());

    }
    // probably not the cleaniest xd
    System.out.println("-".repeat(20));
    ListIterator<Integer> enhancedIt = intLinkedList.listIterator(intLinkedList.size());
    while (enhancedIt.hasPrevious()) {
      // System.out.println(enhancedIt.previous());
      if (enhancedIt.previous().equals(4)) {
        enhancedIt.remove();
      }
    }
    System.out.println(intLinkedList);
  }

}

record GrosseryItem(String name, String type, int count) {
  public GrosseryItem(String diaryItemName) {
    this(diaryItemName, "DIARY", 1);
  }
}