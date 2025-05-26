package advanced;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

// what is this tho  | ui lib
import javax.swing.text.html.HTMLDocument.Iterator;

@SuppressWarnings("unused")
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

        // 7.
        // File Listings

        // empty string = CWD
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        Path pathL = Path.of("");
        System.out.println("cwd = " + pathL.toAbsolutePath());
        try (
                // listing
                Stream<Path> paths = Files.list(pathL)) {
            paths
                    .map(InputOutput::listDir)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        try (
                // Walk is recursive
                Stream<Path> paths = Files.walk(pathL, 2)) {
            paths
                    .filter(Files::isRegularFile)
                    .map(InputOutput::listDir)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        // Files Find
        try (
                // Walk is recursive
                Stream<Path> paths = Files.find(pathL, Integer.MAX_VALUE,
                        (p, attr) -> attr.isRegularFile() && attr.size() > 18000)) {
            paths
                    // .filter(Files::isRegularFile)
                    .map(InputOutput::listDir)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));
        System.out.println("_Directory Streams_");
        // glob
        // NIO2 Class
        Path vscodePath = pathL.resolve(".vscode");
        try (var dirs = Files.newDirectoryStream(vscodePath, "*.json")) {
            dirs.forEach(d -> System.out.println(InputOutput.listDir(d)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("_".repeat(30));
        System.out.println("_".repeat(30));

        System.out.println("___________Directory Streams_");
        // glob
        // NIO2 Class
        Path vscodePathJson = pathL.resolve(".vscode");
        try (var dirs = Files.newDirectoryStream(vscodePathJson,
                (p) -> p.getFileName().toString().endsWith("json"))) {
            dirs.forEach(d -> System.out.println(InputOutput.listDir(d)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("_".repeat(30));
        // dfs
        // walkFileTree - Depth Prio DepthFirstSearch type of algo
        // x____x______x_____x____x____x_____x_____x____x
        System.out.println("_".repeat(30));
        Path startingPath = Path.of(".");
        FileVisitor<Path> statsVisitor = new StatsVisitor();
        try {
            Files.walkFileTree(startingPath, statsVisitor);
        } catch (IOException e) {
            System.out.println("error!");
        }
        // 10. Reading Files
        // testfile.txt
        System.out.println("______reading______");
        // try {
        // Files.createFile(Path.of("file.txt"));
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        try (FileReader reader = new FileReader("file.txt")) {
            System.out.println(Files.size(Path.of("file.txt")));
            // by default 1 int at a time
            char[] block = new char[1000];
            int data;
            System.out.println("start");
            // many diskReads = bad
            // fileReader is buffered based on OS and JVM impelementation
            while ((data = reader.read(block)) != -1) {
                // char represented in java as unsigned int (data) , cast it to get char
                String content = new String(block, 0, data);
                // System.out.print((char) data + " ");
                System.out.printf("------> [%d chars] %s%n", data, content);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println();
            System.out.println("finally!");
        }
        System.out.println("______".repeat(20));
        // buffered readers
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))) {
            // String line;
            // while ((line = bufferedReader.readLine()) != null) {
            // System.out.println(line);

            // }
            var lines = bufferedReader.lines()
                    // .map((line) -> line.toUpperCase())
                    .map(String::toUpperCase)
                    .toList();
            var size = lines.size();
            System.out.println("size = " + size);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class StatsVisitor extends SimpleFileVisitor<Path> {
        private int level;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            System.out.println("\t".repeat(level++) + file.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);
            level++;
            System.out.println("\t".repeat(level) + dir.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Objects.requireNonNull(dir);
            if (exc != null)
                throw exc;
            level--;
            System.out.println(dir.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public String toString() {
            return "StatsVisitor []";
        }

    }

    private static String listDir(Path path) {
        try {
            boolean isDir = Files.isDirectory(path);
            FileTime dateField = Files.getLastModifiedTime(path);
            LocalDateTime modDT = LocalDateTime.ofInstant(dateField.toInstant(), ZoneId.systemDefault());
            return String.format("%-20tD %-20tT %-20s %-15s", modDT, modDT,
                    (isDir ? "<DIR>" : Files.size(path) + " Bytes"), path);
        } catch (Exception e) {
            System.out.println("Something went Wrong!");
            return path.toString();
        }
    }

    private static void extraInfo(Path path) {
        try {
            var atts = Files.readAttributes(path, "*");
            atts.entrySet().forEach(System.out::println);
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
