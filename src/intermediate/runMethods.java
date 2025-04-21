package intermediate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import intermediate.domain.Employee;
import intermediate.domain.StoreEmployee;

public class runMethods {
    public static void main(String[] args) {

        System.out.println("Store Members");
        List<StoreEmployee> storeEmployees = new ArrayList<>(List.of(
                new StoreEmployee(10015, "Meg", 2019, "Target"),
                new StoreEmployee(10515, "Joe", 2021, "Walmart"),
                new StoreEmployee(10105, "Tom", 2020, "Macys"),
                new StoreEmployee(10215, "Marty", 2018, "Walmart"),
                new StoreEmployee(10322, "Bud", 2016, "Target")));
        var c0 = new Employee.InnerEmployeeComparator<>();
        var c1 = new Comparator<StoreEmployee>() {

            @Override
            public int compare(StoreEmployee o1, StoreEmployee o2) {
                return o1.getName().compareTo(o2.getName());
            }

        };
        sortIt(storeEmployees, c0);
        sortIt(storeEmployees, c1);

    }

    public static <T> void sortIt(List<T> list,
            Comparator<? super T> comparator) {
        System.out.println("Sorting with Comparator: " + comparator.toString());
        list.sort(comparator);
        for (T t : list) {
            System.out.println(t);

        }
    }

}
