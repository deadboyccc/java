package intermediate;

public abstract class Animal {

  protected String type;
  private String size;
  private double weight;

  public Animal(String type, String size, double weight) {
    this.type = type;
    this.size = size;
    this.weight = weight;
  }

  public abstract void move(String speed);

  public abstract void makeNoise();

  public final String getExplicitType() {
    return getClass().getSimpleName() + " (" + type + ") \n";
  }

}

abstract class Mammals extends Animal {

  public Mammals(String type, String size, double weight) {
    super(type, size, weight);
  }

  @Override
  public void move(String speed) {
    System.out.println(getExplicitType() + "|Mammal| override!");
  }

  public abstract void shedHair();

}
