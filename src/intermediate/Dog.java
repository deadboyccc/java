package intermediate;

public class Dog extends Animal {

  public Dog(String type, String size, double weight) {
    super(type, size, weight);
  }

  @Override
  public void move(String speed) {
    System.out.println(getExplicitType() + "moving " + speed + "ly");

  }

  @Override
  public void makeNoise() {
    if (type == "Wolf") {
      System.out.println("Howling!");
    } else {
      System.out.println("Barking!");
    }
  }

}
