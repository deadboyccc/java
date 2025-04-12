package intermediate;

interface InnerFish {
  // Implicit public static final
  double CONST_VAR = 10.132;

  // Implicit public abstract
  void swim();
}

interface BigInnerFish extends InnerFish {
  //
  void eatSmallFish();

}

interface Trackable {
  void getCoords();

}

enum SwimStages implements Trackable {

  speeding, constVelocity, slowing;

  @Override
  public void getCoords() {
    if (this != constVelocity) {
      System.out.println("Swarm Accelerating");

    }
  }

}

record SharkSwarm(String name, String type) implements BigInnerFish {

  @Override
  public void swim() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'swim'");
  }

  @Override
  public void eatSmallFish() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'eatSmallFish'");
  }
}

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
