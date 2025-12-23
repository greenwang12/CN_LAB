import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class RSA{

    // Generate RSA Key pair
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Encrypt message using public key
    public static String encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt message using private key
    public static String decrypt(String encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }

    // Convert Base64 string to PublicKey
    public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    // Convert Base64 string to PrivateKey
    public static PrivateKey getPrivateKeyFromBase64(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    // Main method
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            // Generate new RSA keys
            KeyPair keyPair = generateKeyPair();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            System.out.println("Generated Public Key:\n" + publicKeyBase64);
            System.out.println("\nGenerated Private Key:\n" + privateKeyBase64);
            System.out.println("\n--- Save these keys for future use ---\n");

            System.out.print("Enter 'E' to Encrypt or 'D' to Decrypt: ");
            String choice = sc.nextLine().trim().toUpperCase();

            if (choice.equals("E")) {
                System.out.print("Enter message to encrypt: ");
                String message = sc.nextLine();
                System.out.print("Enter Public Key (Base64): ");
                String pubKeyInput = sc.nextLine().trim();

                PublicKey publicKey = getPublicKeyFromBase64(pubKeyInput);
                String encryptedMessage = encrypt(message, publicKey);

                System.out.println("\nEncrypted Message:\n" + encryptedMessage);

            } else if (choice.equals("D")) {
                System.out.print("Enter encrypted message (Base64): ");
                String encryptedMessage = sc.nextLine();
                System.out.print("Enter Private Key (Base64): ");
                String privKeyInput = sc.nextLine().trim();

                PrivateKey privateKey = getPrivateKeyFromBase64(privKeyInput);
                String decryptedMessage = decrypt(encryptedMessage, privateKey);

                System.out.println("\nDecrypted Message:\n" + decryptedMessage);
            } else {
                System.out.println("Invalid choice! Please enter 'E' or 'D'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
