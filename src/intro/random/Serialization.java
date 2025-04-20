package intro.random;

import java.io.*;

class Box implements Serializable {

    private double width;
    private double height;
    private double depth;

    public Box() {
        this.width = 1.0;
        this.height = 1.0;
        this.depth = 1.0;
    }

    public Box(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public Box(double width, double height) {
        this.width = width;
        this.height = height;
        this.depth = 1.0;
    }

    public Box(double width) {
        this.width = width;
        this.height = 1.0;
        this.depth = 1.0;
    }

    public double getVolume() {
        return width * height * depth;
    }

    public double getSurfaceArea() {
        return 2 * (width * height + width * depth + height * depth);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Box [width=" + width + ", height=" + height + ", depth=" + depth + "]";
    }
}

public class Serialization {

    public static void main(String[] args) {
        // Box box1 = new Box(12,12);
        try {

            FileInputStream fs = new FileInputStream("foo.ser");
            ObjectInputStream os = new ObjectInputStream(fs);
            Object one = os.readObject();
            Box boxRestored = (Box) one;
            System.out.println(boxRestored);
            BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
            writer.write("hello world!");
            writer.flush();
            Thread.sleep(3000);
            writer.write("hello world!");
            writer.flush();
            Thread.sleep(3000);
            writer.write("\nhello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.write("hello world!");
            writer.close();
            os.close();

            // connection stream connecting to data source
            // it streams the data to chain connection (turns the bytes into obj)

            // FileOutputStream fs = new FileOutputStream("foo.ser");
            // ObjectOutputStream os = new ObjectOutputStream(fs);
            // os.writeObject(box1);
            // os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
