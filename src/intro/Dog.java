package intro;

public class Dog extends Animal {

  private String earShape;

  private String tailSahpe;

  public Dog() {
    super("Doggie", "Medium", 80);
  }

  public Dog(String type, double weight) {
    this(type, "medium", weight, "Perky", "curly");

  }

  public Dog(String type, String size, double weight, String earShape, String tailSahpe) {
    super(type, size, weight);
    this.earShape = earShape;
    this.tailSahpe = tailSahpe;
  }

  @Override
  public String toString() {
    return super.toString() + " Dog [earShape=" + earShape + ", tailSahpe=" + tailSahpe + "]";
  }

  @Override
  public void makeNoise() {
    if (type == "wolf") {

      System.out.println("dog making howling noises!");
    }
    System.out.println("dog making noise!");
  }

  @Override
  public void move(String speed) {
    super.move(speed);
    if (speed == "slow") {
      bark();
    } else {
      run();
    }
    System.out.println("-dog override!");
  }

  private void bark() {
    System.out.print("-dog barked");
  }

  private void run() {
    System.out.print("-dog ran");
  }

}