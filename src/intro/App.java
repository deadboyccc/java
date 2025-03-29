package intro;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.random.*;

@SuppressWarnings("unused")
public class App {
    public static void main(String[] args) throws Exception {

    }

    private static void OOPtest1() {
        Animal animal1 = new Animal("generic Animal", "huge", 400);
        doAnimalStuff(animal1, "slow");
        Dog dog = new Dog();
        Dog yorkie = new Dog("Yorkie", 20);
        Dog rertriever = new Dog("lab", 25);
        Dog wolf = new Dog("wolf", 40);
        // list is an interface
        List<Animal> animalList = new LinkedList<Animal>();
        animalList.add(animal1);
        animalList.add(rertriever);
        animalList.add(yorkie);
        animalList.add(wolf);
        for (Animal animal : animalList) {
            animal.makeNoise();
            animal.move("fast");

        }
        System.out.println("########");
        Fish fish = new Fish("Shark", 40, 2, 3);
        doAnimalStuff(fish, "fast");
    }

    public static void doAnimalStuff(Animal animal, String speed) {
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
        System.out.println("_ _ _ _ _");
    }

    private static void DtoPojo() {
        for (int i = 0; i < 5; i++) {
            LPAStudent p1 = new LPAStudent("1231", "test", "12/12/2020", "cs,js,etc");
            System.out.println(p1);

        }
        for (int i = 0; i < 5; i++) {
            Pojo p1 = new Pojo("1231", "test", "12/12/2020", "cs,js,etc");
            System.out.println(p1);

        }
    }

    private static void constructorOverloadingDefaultValues() {
        Car car1 = new Car();
        car1.setMake("lambo");
        car1.setColor("red");
        car1.describeCar();

        Car car2 = new Car("test", "testModel", "red", 2, true);
    }

    private static void InputParseInt() {
        Scanner cin = new Scanner(System.in);
        System.out.println("num:");
        int a = Integer.parseInt(cin.nextLine());
        System.out.println(a);
        cin.close();
    }

    private static void getRandom() {
        List<Integer> probabilityArr = new ArrayList<Integer>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            probabilityArr.add(random.nextInt(2)); // [0,2)
        }
        System.out.println(probabilityArr);
    }

    private static void testingStaticVars() {
        Human foo = new Human("test", 10);
        System.out.println(Human.population);
    }

    private static void whileLoop() {
        int x = 100;
        while (x > 10) {
            System.out.println(x -= 10);
        }
    }

    private static void binarySearchTestCase() {
        int[] binarySearchArr = { -5, -2, 0, 3, 7, 9, 11, 25 };
        int found = binarySearch(binarySearchArr, 3);
        System.out.println(found);
    }

    private static void classesAndCollections() {
        Human test = new Human("Joe", Long.MAX_VALUE);
        System.out.println(test);
        System.out.println(factorial(5));
        LinkedList<Human> humansList = new LinkedList<>();
        humansList.add(test);
        humansList.add(new Human("doe", Byte.MAX_VALUE));
        for (Human human : humansList) {
            System.out.println(human);
        }
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("a", "first");
        testMap.put("b", "second");
        System.out.println(testMap.get("a"));
    }

    private static void wrapperClasses() {
        System.out.println("Hello, World!");
        int lowBoundInt = Integer.MIN_VALUE;
        int highBoundInt = Integer.MAX_VALUE;
        System.out.println(lowBoundInt);
        System.out.println(highBoundInt);
        byte lowBoundByte = Byte.MIN_VALUE;
        System.out.println(lowBoundByte);
        System.out.println(Long.SIZE);
        System.out.println(Long.MAX_VALUE);
    }

    public static void switchEx() {
        int a = 1;
        switch (a) {
            case 1:
                System.out.println(a);
                break;

            default:
                break;
        }
        int b = 1;

        String result = switch (b) {
            case 1 -> "one";
            default -> "two";
        };

        System.out.println(result);
    }

    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static long factorial(int n) {
        if (n == 1 || n == 0)
            return 1;

        return n * factorial(n - 1);

    }

}