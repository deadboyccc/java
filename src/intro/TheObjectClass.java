package intro;

// just like C# | Object or java.lang.Object
public class TheObjectClass extends java.lang.Object {
  public static void main(String[] args) {
    Student max = new Student("max", 23);
    // hexadecimal hash per instance
    System.out.println(max);
    PrimarySchoolStudent jimmy = new PrimarySchoolStudent("jim", 9, "Joe");
    System.out.println(jimmy);
  }

}

class PrimarySchoolStudent extends Student {
  private String parentName;

  public PrimarySchoolStudent(String name, int age, String parentName) {
    super(name, age);
    this.parentName = parentName;
  }

  @Override
  public String toString() {
    return "[Parent: " + this.parentName + "] " + super.toString();
  }

}

class Student {
  private String name;
  private int age;

  public Student(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public String toString() {
    return super.toString() + "Student [name=" + name + ", age=" + age + "]";
  }

}
