package advanced;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

// what is this tho 
import javax.swing.text.html.HTMLDocument.Iterator;

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
        // Main()
        Path path2 = Path.of("files/testing.txt");
        printPathInfo(path2);
        // printPathInfo(Path.of("files/testing.csv"));
        logStatement(path2);
        System.out.println("_".repeat(20));
        extraInfo(path2);
        System.out.println("_".repeat(20));

        //7.

    }

    private static void extraInfo(Path path) {
        try {

            var atts = Files.readAttributes(path, "*");
            atts.entrySet()
                    .forEach(System.out::println);
            System.out.println(atts);
        } catch (Exception e) {
            System.out.println("problem!!");
        }
    }

    private static void printPathInfo(Path path) {
        System.out.println("------------------");
        System.out.println("Path: " + path);
        System.out.println("fileName = " + path.getFileName());
        System.out.println("parent = " + path.getParent());
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Absolute Path: = " + absolutePath);
        System.out.println("Absolute Path Root: = " + absolutePath.getRoot());
        System.out.println("Root = " + path.getRoot());
        System.out.println("isAbsolute = " + path.isAbsolute());

        System.out.println(absolutePath.getRoot());
        // int i = 1;
        // java.util.Iterator<Path> it = path.toAbsolutePath().iterator();
        // while (it.hasNext()) {
        // System.out.println(".".repeat(i++) + " " + it.next());
        // }
        int pathParts = absolutePath.getNameCount();
        for (int i = 0; i < pathParts; i++) {
            System.out.println(".".repeat(i + 1) + " " + absolutePath.getName(i));
        }

        System.out.println("------------------");
    }

    private static void logStatement(Path path) {
        try {
            Path parent = path.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.writeString(path, Instant.now() + ": hello file world\n", StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
