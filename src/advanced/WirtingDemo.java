package advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WirtingDemo {
    public static void main(String[] args) throws IOException {
        System.out.println();
        try (FileWriter fileWriter = new FileWriter(new File("new.txt"))) {
            for (int i = 0; i < 90; i++) {
                fileWriter.write(new String("fjaowjefa"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Path path = Path.of("test.csv");
        String studentsInfo = """
                name,age,grade
                Alice,20,A
                Bob,22,B
                Charlie,19,A
                """;
        Files.writeString(path, studentsInfo, StandardOpenOption.APPEND);
        // better perf
        List<String> data = new ArrayList<>();
        data.add(studentsInfo);
        Files.write(path, data);

        // buffer Writer
        try (var writer = Files.newBufferedWriter(Path.of("test2.csv"))) {
            writer.write(studentsInfo);
            writer.newLine();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        // Buffer writer in depth
        try (FileWriter writer = new FileWriter("test3.csv")) {
            writer.write(studentsInfo);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //
        // print writer 
        //
        try (PrintWriter writer = new PrintWriter("test4.csv")) {
            writer.println(studentsInfo);
            writer.write(System.lineSeparator());
        

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        // //
        // //

    }

}
