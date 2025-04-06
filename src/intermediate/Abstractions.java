package intermediate;

import java.util.ArrayList;
import java.util.List;

public class Abstractions {
  public static void main(String[] args) {

    Dog dog = new Dog("Wolf", "big", 128);
    Fish fish = new Fish("Goldfish", "medium", 64);
    Monkey monkey = new Monkey("Gorilla", "Large", 1024);
    var animals = new ArrayList<Animal>(List.of(dog, fish, monkey));
    for (var animal : animals) {
      doAnimalStuff(animal);
    }

  }

  private static void doAnimalStuff(Animal animal) {
    animal.makeNoise();
    animal.move("Slow");
  }

}
