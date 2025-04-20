package intro.random;

import java.util.Scanner;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        // String name = scanner.nextLine();
        // System.out.println(name);
        System.out.println(sum(1, 2, 3));

        scanner.close();
        Human a = new Human("Ahmed", 25);
        ArrayList<Human> test = new ArrayList<>();
        test.add(a);
        test.add(new Human("Joe", 22));
        for (Human human : test) {
            System.out.println(human);
        }
    }

    /**
     * @param arr
     * @return
     */
    static double sum(double... arr) {
        double sum = 0;
        for (double num : arr) {
            sum += extracted(num);
        }
        return extracted(sum);

    }

    private static double extracted(double num) {
        return num;
    }
}
