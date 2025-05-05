package advanced;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RandomAccessDemo {
    static class Player implements Serializable {
        private String name;
        private int topScore;
        private List<String> collectedWeapons = new ArrayList<>();

        public Player(String name, int topScore, List<String> collectedWeapons) {
            this.name = name;
            this.topScore = topScore;
            this.collectedWeapons = collectedWeapons;
        }

        @Override
        public String toString() {
            return "Player [name=" + name + ", topScore=" + topScore + ", collectedWeapons=" + collectedWeapons + "]";
        }

    }

    public static void main(String[] args) {

        Path path = Path.of("data.dat");

        try {
            boolean needsInit = !Files.exists(path) || Files.size(path) == 0;
            if (needsInit) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                writeData(path);
            }
            readData(path);
        } catch (IOException e) {
            System.err.println("I/O error on " + path + ":");
            e.printStackTrace();
        }
        System.out.println("_".repeat(40));
        Player joe = new Player("joe", 100000, List.of("joedino", "joebomb", "joenuke"));
        System.out.println(joe);

        System.out.println("_".repeat(40));
        Path joeFile = Path.of("joe.dat");

        writeObj(path, joe);
        var newOne = readObj(path);

        System.out.println("_".repeat(40));
        System.out.println(newOne);
        System.out.println("_".repeat(40));
    }

    private static void writeObj(Path target, Player p) {
        try (ObjectOutputStream objStream = new ObjectOutputStream(Files.newOutputStream(target))) {

            objStream.writeObject(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private static Player readObj(Path p) {
        try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(p))) {
            Player player = (Player) stream.readObject();
            return player;

        } catch (Exception e) {
            // TODO: handle exception
            return new Player("null", 0, null);
        }
    }

    private static void writeData(Path target) throws IOException {
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(target.toFile())))) {

            int myInt = 17;
            long myLong = 100_000_000_000_000L;
            boolean myBool = true;
            char myChar = 'Z';
            float myFloat = 1.2f;
            double myDouble = 12.123;
            String myString = "Hello World";

            long before = out.size();
            out.writeInt(myInt);
            System.out.println("writeInt wrote:    " + (out.size() - before) + " bytes");

            before = out.size();
            out.writeLong(myLong);
            System.out.println("writeLong wrote:   " + (out.size() - before) + " bytes");

            before = out.size();
            out.writeBoolean(myBool);
            System.out.println("writeBoolean wrote:" + (out.size() - before) + " bytes");

            before = out.size();
            out.writeChar(myChar);
            System.out.println("writeChar wrote:   " + (out.size() - before) + " bytes");

            before = out.size();
            out.writeFloat(myFloat);
            System.out.println("writeFloat wrote:  " + (out.size() - before) + " bytes");

            before = out.size();
            out.writeDouble(myDouble);
            System.out.println("writeDouble wrote: " + (out.size() - before) + " bytes");

            before = out.size();
            out.writeUTF(myString);
            System.out.println("writeUTF wrote:    " + (out.size() - before) + " bytes");
        }
    }

    private static void readData(Path target) throws IOException {
        try (DataInputStream in = new DataInputStream(
                new FileInputStream(target.toFile()))) {
            System.out.println(in.readInt());
            System.out.println(in.readLong());
            System.out.println(in.readBoolean());
            System.out.println(in.readChar());
            System.out.println(in.readFloat());
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
        }
    }
}
