package advanced;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChainingLambdas {
    public static void main(String[] args) {
        String name = "Joe";
        Function<String, String> uCase = String::toUpperCase;
        System.out.println(uCase.apply("Test"));
        Function<String, String[]> f0 = uCase
                .andThen((s) -> s.concat(" Ali"))
                .andThen((s) -> s.split(" "));
        Function<String, String> f1 = uCase
                .andThen((s) -> s.concat(" Ali"))
                .andThen((s) -> s.split(" "))
                .andThen((s -> s[1].toUpperCase() + "," + s[0].toLowerCase()));

        Function<String, Integer> f2 = uCase
                .andThen((s) -> s.concat(" Ali"))
                .andThen((s) -> s.split(" "))
                .andThen((s -> s[1].toUpperCase() + "," + s[0].toLowerCase()))
                .andThen(String::length);

        System.out.println("length: " + f2.apply(("Joejoejoe")));
        System.out.println(Arrays.toString(f0.apply("Ahmed")));
        System.out.println("*".repeat(20));
        System.out.println(f1.apply("hello"));
        System.out.println("*".repeat(20));

        Function<String, String> lastName = (s) -> s.concat("Ali");
        // chaining
        Function<String, String> uCaseLastName = uCase.andThen(lastName);
        System.out.println(uCaseLastName.apply(name));
        uCaseLastName = uCase.compose(lastName);

        System.out.println("_".repeat(30));
        String[] names = { "Ahmed", "Ali", "Joe", "Biden" };
        Consumer<String> c1 = (s) -> System.out.println(s.charAt(0));
        Consumer<String> c2 = System.out::println;

        Arrays.asList(names).forEach(c1.andThen(s -> System.out.println("()()()()()")).andThen(c2));
        System.out.println("_".repeat(30));

    }

}
