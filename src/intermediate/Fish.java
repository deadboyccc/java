package intermediate;

public class Fish extends Animal {

  public Fish(String type, String size, double weight) {
    super(type, size, weight);
  }

  @Override
  public void move(String speed) {
    System.out.println(getExplicitType() + "swimming " + speed + "ly");

  }

  @Override
  public void makeNoise() {
    if (type == "Goldfish") {
      System.out.println("Goldieee!");
    } else {
      System.out.println("Not Goldieeeeee :|!");
    }
  }

}
