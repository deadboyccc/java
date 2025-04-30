package advanced;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputOutput {
    public static void main(String[] args) {
        // cwd pwd

        System.out.println("Current Working Directory(cwd): " +
                new File("").getAbsolutePath());

        //
        String filename = "files/testing.csv";
        testFile2(filename);
        File file = new File(filename);
        var absPath = file.getAbsolutePath();
        System.out.println(absPath);
        if (!file.exists()) {
            System.out.println("file doesn't exist!");
            return;
        }
        System.out.println("_".repeat(20));
        for (File f : File.listRoots()) {
            System.out.println(f);
        }
        System.out.println("_".repeat(20));

        // file is a handler (ref to os file on disk)
        // FileReader is the actual data possibly buffered and cached in memory

        // NIO2
        Path path = Paths.get("files/testing.csv");
        if (!Files.exists(path)) {
            System.out.println("2. Doesn't Exist!");
            return;
        }
        System.out.println("2. exists! ");

        // 5 file and path
        System.out.println("_____".repeat(30));
        useFile("testfile.txt");
        System.out.println("_____".repeat(30));
        usePath("pathfile.txt");
        System.out.println("_____".repeat(30));

        System.out.println("6-IO");
    }

    private static void useFile(String fileName) {
        File file = new File(fileName);
        boolean fileExists = file.exists();
        System.out.printf("File '%s' %s%n", fileName, fileExists ? " exists!" : " doesn't exist!");
        if (fileExists) {
            System.out.println("deleting...");
            // failed delete = false
            fileExists = !file.delete();

        }
        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("something went wrong!");
            }
            System.out.println("file created! ");
            if (file.canWrite()) {
                System.out.println("writable! ");
            }
        }
    }

    private static void usePath(String fileName) {
        Path path = Path.of(fileName);
        boolean fileExists = Files.exists(path);
        System.out.printf("File '%s' %s%n", fileName, fileExists ? " exists!" : " doesn't exist!");
        if (fileExists) {
            System.out.println("deleting...");
            try {

                Files.delete(path);
                fileExists = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!fileExists) {
            try {
                Files.createFile(path);
                System.out.println("file created! ");
                if (Files.isWritable(path)) {
                    System.out.println("writable! ");
                    Files.writeString(path, "henlooo");
                    System.out.println("wrote on the file!");
                }
                System.out.println("I can read too");
                Files.readAllLines(path).forEach(System.out::println);
                ;
            } catch (IOException e) {
                System.out.println("something went wrong!");
            }
        }
    }

    @SuppressWarnings("unused")
    private static void testFile(String filename) {
        Path path = Paths.get(filename);
        FileReader fileReader = null;

        try {
            // List<String> lines = Files.readAllLines(path);
            fileReader = new FileReader(filename);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("finally!");
        }
    }

    private static void testFile2(String filename) {
        try (FileReader fileReader = new FileReader(filename)) {
        } catch (IOException e) {
            System.out.println("IO Exception! " + e.getMessage());
        } finally {
            System.out.println("logging");
        }
        System.out.println("file exists!");
    }

}
