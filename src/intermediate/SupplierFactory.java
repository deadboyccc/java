package intermediate;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class SupplierFactory {
    public static void main(String[] args) {
        String[] names = { "Donald", "Cina", "Ju", "Joe", "Ahmed", "Ali", "joe", "Biden", "Mohammed", "Zoho" };
        Supplier<Integer> s1 = () -> new Random().nextInt(0, names.length);
        String[] randomStrings = getRandomValues(12, names, s1);
        System.out.println(Arrays.toString(randomStrings));

    }

    public static String[] getRandomValues(
            int count,
            String[] values,
            Supplier<Integer> s) {
        String[] resultStrings = new String[count];
        for (int i = 0; i < count; i++) {
            resultStrings[i] = values[s.get()];

        }
        return resultStrings;

    }

}
