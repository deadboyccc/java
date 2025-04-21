package intermediate.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StoreEmployee extends Employee {
    private String store;

    public StoreEmployee() {
    }

    public StoreEmployee(int id, String name, int yearStarted) {
        super(id, name, yearStarted);
    }

    public StoreEmployee(String store) {
        this.store = store;
    }

    public StoreEmployee(int id, String name, int yearStarted, String store) {
        super(id, name, yearStarted);
        this.store = store;
    }

    @Override
    public String toString() {
        return "StoreEmployee []";
    }

    public class InnerStoreEmployee<T extends StoreEmployee> implements Comparator<StoreEmployee> {

        @Override
        public int compare(StoreEmployee o1, StoreEmployee o2) {
            int result = o1.store.compareTo(o2.store);
            if (result == 0) {
                return new Employee.InnerEmployeeComparator<>().compare(o1, o2);
            }
            return result;
        }

    }

    public void setStore(String store) {
        this.store = store;
    }

    public static void main(String[] args) {
        System.out.println("Store Members");
        List<StoreEmployee> storeEmployees = new ArrayList<>(List.of(
                new StoreEmployee(10015, "Meg", 2019, "Target"),
                new StoreEmployee(10515, "Joe", 2021, "Walmart"),
                new StoreEmployee(10105, "Tom", 2020, "Macys"),
                new StoreEmployee(10215, "Marty", 2018, "Walmart"),
                new StoreEmployee(10322, "Bud", 2016, "Target")));

        storeEmployees.sort(new StoreEmployee().new InnerStoreEmployee<>());
        System.out.println(storeEmployees);
        System.out.println("local classes");
        addPigLatinName(storeEmployees);
    }

    public static void addPigLatinName(List<? extends StoreEmployee> list) {

        class DecoratedEmployee extends StoreEmployee {
            private String piglatinName;
            private Employee originalInstance;

            public DecoratedEmployee(String piglatinName, Employee originalInstance) {
                this.piglatinName = piglatinName;
                this.originalInstance = originalInstance;
            }

            @Override
            public String toString() {
                return super.toString() + " | " + piglatinName;
            }

        }
        List<DecoratedEmployee> newList = new ArrayList<>(list.size());
        for (var employee : list) {
            String name = employee.getName();
            String piglatin = name.substring(1) + name.charAt(0) + "ay";
            newList.add(new DecoratedEmployee(piglatin, employee));
        }
        for (DecoratedEmployee decoratedEmployee : newList) {
            System.out.println(decoratedEmployee);

        }

    }
}
