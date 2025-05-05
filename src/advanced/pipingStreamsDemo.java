package advanced;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class pipingStreamsDemo {
    public static void main(String[] args) throws IOException {
        String inputFile = "file.txt";
        String outputFile = "file-backup.txt";

        File file = new File(inputFile);

        if (!file.exists()) {
            System.err.println("Error: Input file '" + inputFile + "' does not exist.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                PrintWriter writer = new PrintWriter(outputFile)) {
            // Piping
            reader.transferTo(writer);

            System.out.println("Data successfully piped from '" + inputFile + "' to '" + outputFile + "'.");
        }

        // http req
        String urlString = "https://api.census.gov/data/2019/pep/charagegroups?get=NAME,POP&for=state:*";
        URI uri = URI.create(urlString);

        try (var urlInputStream = uri.toURL().openStream()) {
            // open a stream + making the req
            // urlInputStream.transferTo(System.out);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        Path jsonPath = Path.of("usPopBystate.txt");
        try (var reader = new InputStreamReader(uri.toURL().openStream())) {
            try (var writer = Files.newBufferedWriter(jsonPath)) {
                reader.transferTo(writer);
            }
            // var writer = Files.newBufferedWriter(jsonPath);
            // reader.transferTo(writer);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // transforming json to csv
        try (var reader = new InputStreamReader(uri.toURL().openStream())) {
            try (PrintWriter writer = new PrintWriter("usPop.csv")) {
                reader.transferTo(new Writer() {

                    @Override
                    public void write(char[] cbuf, int off, int len) throws IOException {
                        // TODO Auto-generated method stub
                        String jsonString = new String(cbuf, off, len).trim();
                        jsonString = jsonString.replace('[', ' ').trim();
                        jsonString = jsonString.replaceAll("\\]", "");
                        writer.write(jsonString);
                        // throw new UnsupportedOperationException("Unimplemented method 'write'");
                    }

                    @Override
                    public void flush() throws IOException {
                        // TODO Auto-generated method stub
                        writer.flush();
                    }

                    @Override
                    public void close() throws IOException {
                        // TODO Auto-generated method stub
                        writer.close();
                    }

                });
            }
            // var writer = Files.newBufferedWriter(jsonPath);
            // reader.transferTo(writer);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}