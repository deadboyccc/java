package intro;

public class Human {
  String name;
  long age;

  public Human(String s, long maxValue) {
    this.name = s;
    this.age = maxValue;
  }

  @Override
  public String toString() {
    return this.name + " " + this.age;
  }

}
