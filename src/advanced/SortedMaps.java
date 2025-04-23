package advanced;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class SortedMaps {
    private static Map<String, Purchase> purchases = new LinkedHashMap<>();
    private static NavigableMap<String, Student> students = new TreeMap<>();

    public static void main(String[] args) {
        Course jmc = new Course("jmc101", "Java MC", "Java lang");
        Course pyhthon = new Course("python101", "Python MC", "Python lang");
        addPurchase("Mary", jmc, 120);
        addPurchase("Mary", pyhthon, 80);
        addPurchase("Ali", jmc, 130);
        addPurchase("Joe", jmc, 140);
        addPurchase("Bill", pyhthon, 123);
        System.out.println("_".repeat(30));
        // insertion order linkedList
        purchases.forEach((k, v) -> System.out.println(k + ": " + v));

        // sorted BSTree
        System.out.println("_".repeat(30));
        students.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("_".repeat(30));

        NavigableMap<LocalDate, List<Purchase>> datedPurchases = new TreeMap<>();
        for (Purchase purchase : purchases.values()) {
            datedPurchases.compute(purchase.purchaseDate(), (pdate, plist) -> {
                List<Purchase> list = plist == null ? new ArrayList<>() : plist;
                list.add(purchase);
                return list;

            });
        }
        datedPurchases.forEach((k, v) -> System.out.println(k + ": "));

    }

    private static void addPurchase(String name, Course course, double price) {
        Student existeningStudent = students.get(name);
        if (existeningStudent == null) {
            existeningStudent = new Student(name, course);
            students.put(name, existeningStudent);
        } else {
            existeningStudent.addCourse(course);
        }
        int day = new Random().nextInt(1, 29); // Ensure valid day of the month (1-28)
        String key = course.courseId() + "_" + existeningStudent.getId();
        int year = LocalDate.now().getYear();
        Purchase purchase = new Purchase(course.courseId(), existeningStudent.getId(), price, year,day);
        purchases.put(key, purchase);
    }

}
