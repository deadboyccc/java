package intro;

public class Car {
  // the default = package private
  // package = assembly in C#
  private String make;
  private String model;
  private String color;
  private int doors;
  private boolean convertible;

  // Setters and Getters
  public String getMake() {
    return make;
  }

  public String getColor() {
    return color;
  }

  public int getDoors() {
    return doors;
  }

  public String getModel() {
    return model;
  }

  public boolean isConvertable() {
    return convertible;
  }

  public void describeCar() {
    // default values for primitives and null for reftype
    System.out.println("Car Information:");
    System.out.println("  Make:        " + this.make);
    System.out.println("  Model:       " + this.model);
    System.out.println("  Color:       " + this.color);
    System.out.println("  Doors:       " + this.doors);
    System.out.println("  Convertible: " + (this.convertible ? "Yes" : "No"));
  }

  public Car() {
    super();
  }
}