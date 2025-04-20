package intermediate.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private int yearStarted;

    public Employee() {
    }

    public Employee(int id, String name, int yearStarted) {
        this.id = id;
        this.name = name;
        this.yearStarted = yearStarted;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearStarted() {
        return yearStarted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearStarted(int yearStarted) {
        this.yearStarted = yearStarted;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", yearStarted=" + yearStarted + "]";
    }

    public static class InnerEmployeeComparator<T extends Employee> implements Comparator<Employee> {

        @Override
        public int compare(Employee o1, Employee o2) {
            return Integer.valueOf(o1.id).compareTo(Integer.valueOf(o2.id));
        }

    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee(10001, "Ralph", 2015),
                new Employee(10005, "Carole", 2021),
                new Employee(10022, "Jane", 2013),
                new Employee(13151, "Laura", 2020),
                new Employee(10050, "Jim", 2018)));
        employees.sort(new Employee.InnerEmployeeComparator<Employee>());
        System.out.println(employees);

    }

}
