package intro;

public class Human {
  String name;
  long age;
  public static int population = 0;

  public Human(String s, long maxValue) {
    population++;
    this.name = s;
    this.age = maxValue;
  }

  @Override
  public String toString() {
    return this.name + " " + this.age;
  }

}
