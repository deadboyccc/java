package intermediate;

public class Monkey extends Mammals {

  public Monkey(String type, String size, double weight) {
    super(type, size, weight);
  }

  @Override
  public void shedHair() {
    System.out.println(getExplicitType() + " shedding hair!");
  }

  @Override
  public void makeNoise() {
    System.out.println(getExplicitType() + " making noise!");
  }

}
