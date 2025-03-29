package intro;

public class Car {
  // the default = package private
  // package = assembly in C#
  private String make;
  private String model;
  private String color;
  private int doors;
  private boolean convertible;

  // Constructor
  public Car() {
    this("Unkown Make", "Unkown Model", "Unkown", -1, false);
  }

  public Car(String make) {
    this(make, "default", "default", 2, true);
  }

  public Car(String make, String model, String color, int doors, boolean convertible) {
    this.make = make;
    this.model = model;
    this.color = color;
    this.convertible = convertible;

  }

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

  public void setColor(String color) {
    this.color = color;
  }

  public void setConvertible(boolean convertible) {
    this.convertible = convertible;
  }

  public void setDoors(int doors) {
    this.doors = doors;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public void setModel(String model) {
    this.model = model;
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

}