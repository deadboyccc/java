package intro.random;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class BufferedFileWriterExample {
    public static void main(String[] args) {
        String data = "This is some data written to the file.";

        // 1) Write the file (no need to call out.close() explicitly)
        try (Writer out = new BufferedWriter(new FileWriter("test.txt"))) {
            out.write(data);
            System.out.println("Data written to test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2) Read the file correctly
        try (BufferedReader in = new BufferedReader(new FileReader("test.txt"))) {
            String line;
            // Read *and* assign to 'line' in the condition, then process it
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
