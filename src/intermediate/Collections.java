package intermediate;

import java.util.ArrayList;

public class Collections {
  public static void main(String[] args) {

    // var should be the norm
    var objectList = new ArrayList<GrosseryItem>();
    objectList.add(new GrosseryItem("Milk"));
    // <> diamond operator :
    objectList.add(new GrosseryItem("Butter"));
    objectList.add(new GrosseryItem("Yogurt"));

  }

}

record GrosseryItem(String name, String type, int count) {
  public GrosseryItem(String diaryItemName) {
    this(diaryItemName, "DIARY", 1);
  }
}