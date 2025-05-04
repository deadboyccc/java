package advanced;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NIO2Demo {
    public static void main(String[] args) {
        // characters sets overview
        // chars to ints cuz computers
        // ASCI and Unicode

        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());

        // Reading bytes
        Path path = Path.of("file.txt");
        try {

            System.out.println(new String(Files.readAllBytes(path)));
            System.out.println("_".repeat(30));

            System.out.println(Files.readString(path));
            System.out.println("_".repeat(30));

            Pattern p = Pattern.compile("[a-zA-Z]{12,}");
            Set<String> values = new TreeSet<>();
            Files.readAllLines(path).forEach(s -> {
                Matcher m = p.matcher(s);
                while (m.find()) {
                    values.add(m.group());
                }
            });
            System.out.println(")))))))))))))))))))))");
            System.out.println(values);
            // cleaner
            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        .map(m -> m.group())
                        .distinct()
                        .sorted()
                        .toArray();
                System.out.println(Arrays.toString(results));

            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
