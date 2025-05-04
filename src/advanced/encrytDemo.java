package advanced;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

public class encrytDemo {
    public static void main(String[] args)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeySpecException,
            InvalidKeyException,
            InvalidParameterSpecException,
            IllegalBlockSizeException,
            BadPaddingException,
            InvalidAlgorithmParameterException {

        // Input text to encrypt and password for key generation
        var input = "hello world";
        var password = "my_password";

        // Initialize AES cipher with CBC mode and PKCS5 padding
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Constants for salt size, iteration count, and key length
        final var SALT_SIZE = 8;
        final var ITERATION_COUNT = 65536;
        final var KEY_LENGTH = 256;

        // Generate a random salt
        var salt = new byte[SALT_SIZE];
        SecureRandom.getInstanceStrong().nextBytes(salt);

        // Derive a secret key using PBKDF2 with HMAC-SHA256
        var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        var spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        var secretKey = factory.generateSecret(spec);
        var secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // Encrypt the input text
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        var cipherParameters = cipher.getParameters();
        var iv = cipherParameters.getParameterSpec(IvParameterSpec.class).getIV(); // Get initialization vector
        var ciphertext = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        // Encode the ciphertext in Base64 for readability
        var base64Encoded = Base64.getEncoder().encode(ciphertext);
        System.out.println("Encrypted & Base64 Encoded:");
        System.out.println(new String(base64Encoded));

        // Decrypt the Base64-encoded ciphertext
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        var base64Decoded = Base64.getDecoder().decode(base64Encoded);
        String output = new String(cipher.doFinal(base64Decoded), StandardCharsets.UTF_8);
        System.out.println("Decrypted Text:");
        System.out.println(output);
    }
}
