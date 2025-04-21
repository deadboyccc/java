package intermediate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LambdaIntro1 {
    // implicit static - inner classes
    record Person(String firstName, String lastName) {
        @Override
        public final String toString() {
            return firstName + " " + lastName;
        }
    }

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>(Arrays.asList(new Person("Ahmed", "Ali"),
                new Person("Joe", "Biden"),
                new Person("John", "Cina"),
                new Person("Zaid", "Sami")));

        var comparatorLastName = new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.lastName.compareTo(o2.lastName);
            }

        };
        // lamda
        people.sort((o1, o2) -> o1.lastName.compareTo(o2.lastName));
        System.out.println(people);

        interface EhancedComparator<T> extends Comparator<T> {
            int secondLevel(T o1, T o2);
        }
        var enhancedComparator = new EhancedComparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                int result = o1.lastName.compareTo(o2.lastName);
                if (result == 0) {
                    return secondLevel(o1, o2);
                }
                return result;
            }

            @Override
            public int secondLevel(Person o1, Person o2) {
                return o1.firstName.compareTo(o2.firstName);
            }
        };
        people.sort(enhancedComparator);
        System.out.println(people);
    }

}
