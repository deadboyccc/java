import java.io.*;

public class Reader {
    
    public static void main(String[] args) {
          String fileName = "test.txt"; // The file to read from

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            Integer counter = 0;
            System.out.println("Starting to read from file: " + fileName);

            while ((line = reader.readLine()) != null) {
                counter++;
                System.out.println("Read line: " + counter.toString()+"\n" + line);

                // System.out.println("Pausing for 1 seconds...");
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    System.err.println("Thread sleep interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println("Finished reading the file.");

        } catch (IOException e) {
            // Handle potential file reading errors
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            // e.printStackTrace(); // Uncomment for detailed stack trace
        }

        
    }
}
