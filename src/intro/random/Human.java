package intro.random;

public class Human {
    String name;
    int age;

    Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void walk() {
        System.out.println("walking!");
    }

    @Override
    public String toString() {
        return this.name + " " + this.age;
    }
}
