package advanced;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Base64endeDemo {

    public static void main(String[] args) {
        String originalString = "Hello, Base64 in Java!";

        // 1. Encoding
        // Get the bytes of the string
        byte[] originalBytes = originalString.getBytes(StandardCharsets.UTF_8);

        // Get an encoder
        Base64.Encoder encoder = Base64.getEncoder();

        // Encode the bytes to a Base64 byte array
        byte[] encodedBytes = encoder.encode(originalBytes);

        // Convert the encoded bytes to a String (optional, but common for
        // display/storage)
        String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        System.out.println("Original String: " + originalString);
        System.out.println("Encoded String: " + encodedString);

        // 2. Decoding
        // Get a decoder
        Base64.Decoder decoder = Base64.getDecoder();

        // Decode the Base64 string (or byte array) back to original bytes
        byte[] decodedBytes = decoder.decode(encodedString.getBytes(StandardCharsets.UTF_8));

        // Convert the decoded bytes back to a String
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println("Decoded String: " + decodedString);

        // Shorter way for common cases:
        String shortEncoded = Base64.getEncoder().encodeToString(originalBytes);
        System.out.println("Short Encoded: " + shortEncoded);

        byte[] shortDecodedBytes = Base64.getDecoder().decode(shortEncoded);
        String shortDecoded = new String(shortDecodedBytes, StandardCharsets.UTF_8);
        System.out.println("Short Decoded: " + shortDecoded);
   }
}
