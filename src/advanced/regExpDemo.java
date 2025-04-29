package advanced;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regExpDemo {
    public static void main(String[] args) {
        System.out.println("_________".repeat(20));

        String helloWorld = "%s %s".formatted("hello", "world");
        String helloWorld2 = String.format("%s %s", "hello", "world");

        System.out.println("Using .formatted: " + helloWorld);
        System.out.println("using String.format: " + helloWorld2);

        System.out.println("_________".repeat(20));

        String helloWorld3 = regExpDemo.format("%s %s", "hello", "world");
        System.out.println(helloWorld3);

        //
        // Regex
        // String testString = "Anyone can learn abc's, 123's, and any regular
        // expression";
        String testString = "abc abcabcabc dddddabcddddd 12 123 1234";
        String replacement = "(-)";
        // String[] patterns = { "a|b|c", "[123]", "[A]" };
        String[] patterns = { "[a-z]*^", "[123]{3,}.$", "[A]", "[a-z]{9}" };

        //
        System.out.println("_________".repeat(20));
        System.out.println("Original: " + testString);
        System.out.println("_________".repeat(20));
        for (String pattern : patterns) {
            String output = testString.replaceFirst(pattern, replacement);
            System.out.println("Pattern: " + pattern + " => " + output);

        }
        System.out.println("_________".repeat(20));
        //

        // Song of the Witches in Macbeth, a Play by Shakespeare
        String paragraph = """
                Double, double toil and trouble;
                Fire burn and caldron bubble.
                Fillet of a fenny snake,
                In the caldron boil and bake
                Eye of newt and toe of frog,
                Wool of bat and tongue of dog,
                Adder's fork and blind-worm's sting,
                Lizard's leg and howlet's wing,
                For a charm of powerful trouble,
                Like a hell-broth boil and bubble.
                """;

        // Unicode Break
        String[] lines = paragraph.split("\\R");
        System.out.println("This paragraph has " + lines.length + " lines");
        String[] words = paragraph.split("\\s");
        System.out.println("This paragraph has " + words.length + " words");

        System.out.println(paragraph.replaceAll("[a-zA-Z]+ble", "[-GRUB-]"));

        Scanner scanner = new Scanner(paragraph);
        System.out.println(scanner.delimiter());
        scanner.useDelimiter("\\R");
        // while (scanner.hasNext()) {
        // String element = scanner.next();
        // System.out.println(element);

        // }
        // stream api
        scanner.useDelimiter("\\R");
        // scanner.tokens()
        // .map(s -> s.replaceAll("\\p{Punct}", ""))
        // .flatMap(s -> Arrays.stream(s.split("\\s+")))
        // .filter(s -> s.matches("[a-zA-Z]+ble"))
        // .forEach(System.out::println);
        for (int i = 0; i < 10; i++) {

            var test = scanner.findInLine("[a-zA-Z]+ble");
            System.out.println(test);
        }
        scanner.close();

        System.out.println("_______".repeat(20));

        // mini challanges
        // "Hello, World!"

        String sentence = "Hello, World!";
        Boolean isMatching = sentence.matches("Hello, World!");
        System.out.println(isMatching);

        // 2
        String pattern2 = "^[A-Z][\\p{all}]+[.?!]$";
        for (String s : List.of("The bike is red, and has flat tires.",
                "I Love being  a new L.P.A student!",
                "Hello, friends and family: Welcome!",
                "How are you, Mary?")) {
            boolean matched = s.matches(pattern2);
            System.out.println(matched + ": " + s);
        }

        System.out.println("_______".repeat(20));

        // Pattern Class
        String sentenceVar = "I like motocycles.";
        boolean isBool = Pattern.matches("[A-Z].*[.]", sentenceVar);
        System.out.println(isBool);

        // compiling a pattern (faster)
        Pattern firstPattern = Pattern.compile("[A-Z].*");
        Matcher matcher = firstPattern.matcher(sentence);
        System.out.println(matcher.matches() + ": " + sentence);
        System.out.println("sentence.length: " + sentence.length());
        System.out.println("Matched Ending Index: " + matcher.end());

        System.out.println(matcher.lookingAt() + ": " + sentence);
        System.out.println("Matched Ending Index: " + matcher.end());


        // matches 7 regex

    }
    // Character Classes
    // Bracket [] occurence of first instance
    // [Characters+digits]

    // Period . = any character
    // [.] exact literal "."

    // | = OR

    // [A-Z] [1-9] [a-zA-Z]

    // Quantifiers SIX :
    // * = matches 0 or more of element

    private static String format(String regExp, String... args) {
        int i = 0;
        // .2 .3 .*
        while (regExp.matches(".*%s.*")) {

            regExp = regExp.replaceFirst("%s", args[i++]);
        }

        return regExp;
    }

    private static String format2(String regExp, String... args) {
        int i = 0;
        while (regExp.contains("%s")) {
            regExp = regExp.replaceFirst("%s", args[i++]);
        }

        return regExp;
    }

}
