package intermediate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

// comparable interface
public class ComparableDemo {
    public static void main(String[] args) {

        Integer five = 5;
        int[] others = { 0, 5, 10, -50, 50 };
        for (Integer i : others) {
            int val = five.compareTo(i);
            System.out.printf("%d %s %d: compareTo=%d%n", five,
                    (val == 0 ? "==" : (val < 0) ? "<" : ">"), i, val);
        }

        String banana = "banana";
        String[] fruit = { "apple", "banana", "pear", "BANANA" };

        for (String s : fruit) {
            int val = banana.compareTo(s);
            System.out.printf("%s %s %s: compareTo=%d%n", banana,
                    (val == 0 ? "==" : (val < 0) ? "<" : ">"), s, val);
        }
        Arrays.sort(fruit);
        System.out.println(Arrays.toString(fruit));

        System.out.println("A:" + (int) 'A' + " " + "a:" + (int) 'a');
        System.out.println("B:" + (int) 'B' + " " + "b:" + (int) 'b');
        Student tim = new Student("Tim");
        Student[] students = { new Student("Bob"), new Student("Alice"),
                new Student("Tim"), new Student("Zoe") };
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));
        Arrays.sort(students, new StudentGPAComparator().reversed());

    }

}

class StudentGPAComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        int gpaComparison = Double.compare(o1.GPA, o2.GPA);
        return gpaComparison != 0 ? gpaComparison : o1.name.compareTo(o2.name);
    }

}

class Student implements Comparable<Student> {

    private static int LAST_ID = 1000;
    private static Random random = new Random();

    String name;
    private int studentId;
    protected double GPA;

    public Student(String name) {

        this.name = name;
        this.studentId = LAST_ID++;
        this.GPA = 1.0 + (3.0 * random.nextDouble());
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", studentId=" + studentId + ", GPA=" + GPA + "]";
    }

    @Override
    public int compareTo(Student o) {
        // non generic
        // Student student = (Student) o;
        return Integer.valueOf(this.studentId).compareTo(Integer.valueOf(o.studentId));
        // return this.name.compareTo(o.name);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

}