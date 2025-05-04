package advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class ScannerClassInDepthDemo {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("file.txt"))) {
            // autoClose
            // System.in = inputStream
            // 1 per program
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());

            }
            System.out.println("_____________".repeat(20));
            // stream API method
            System.out.println(scanner.delimiter());
            scanner.useDelimiter("$");
            System.out.println(scanner.delimiter());
            scanner.tokens()
                    .forEach(System.out::println);

            // words 10char+
            scanner.findAll("a-zA-Z{5,}")
                    .map(MatchResult::group)
                    .distinct()
                    .sorted()
                    .forEach(System.out::println);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
