package intro;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int lowBoundInt = Integer.MIN_VALUE;
        int highBoundInt = Integer.MAX_VALUE;
        System.out.println(lowBoundInt);
        System.out.println(highBoundInt);
        byte lowBoundByte = Byte.MIN_VALUE;
        System.out.println(lowBoundByte);
        System.out.println(Long.SIZE);
        System.out.println(Long.MAX_VALUE);
        Human test = new Human("Joe", Long.MAX_VALUE);
        System.out.println(test);
    }
}