public class testDemoJunit {
  public static void main(String[] args) {
    System.out.println("test");
    int a = 0;
    a = addOne(a);
    System.out.println(a);
  }

  private static int addOne(int x) {
    return x + 1;
  }

}
