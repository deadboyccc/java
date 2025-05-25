package advanced;

import java.util.Base64;
import java.nio.charset.StandardCharsets; // Recommended for explicit character encoding

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

        // --- Encoding Section ---

        // Create a new sample String to encode
        String sample = "Gemini is a powerful LLM."; // Changed string

        // Print the actual String
        System.out.println("Sample String (for encoding):\n" + sample);

        // Encode the String into Base64 format
        // It's good practice to specify the character set when getting bytes from a
        // String
        String basicBase64format = Base64.getEncoder()
                .encodeToString(sample.getBytes(StandardCharsets.UTF_8));

        // Print the encoded String
        System.out.println("Encoded String:\n" + basicBase64format);

        // --- Decoding Section ---

        // Create an encoded String to decode (this should match the Base64 of the new
        // sample string)
        String encoded = "R2VtaW5pIGlzIGEgcG93ZXJmdWwgTExNLg=="; // Updated to match the new sample string's encoding

        // Print the encoded String (for decoding)
        System.out.println("\nEncoded String (for decoding):\n" + encoded);

        // Decode the Base64 string back into a byte array
        byte[] actualByte = Base64.getDecoder()
                .decode(encoded);

        // Convert the byte array back to a String, specifying the original character
        // set
        String actualString = new String(actualByte, StandardCharsets.UTF_8);

        // Print the actual (decoded) String
        System.out.println("Decoded String:\n" + actualString);
    }
}