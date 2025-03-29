package intro;

public class Fish extends Animal {
  private int fins;
  private int gills;

  public Fish(String type, double weight, int fins, int gills) {
    super(type, "small", weight);
    this.fins = fins;
    this.gills = gills;
  }

  private void moveMuslces() {
    System.out.println("fish moving muscles!");
  }

  private void moveBackFin() {
    System.out.println("fish backfin  moving");
  }

  @Override
  public void move(String speed) {
    super.move(speed);
    moveMuslces();
    if (speed == "fast") {
      moveBackFin();
    }
    System.out.println("-done finish move()-");
  }

  @Override
  public String toString() {
    return super.toString() + "Fish [fins=" + fins + ", gills=" + gills + "]";
  }

}
