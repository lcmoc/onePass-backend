package ch.bbw.onePass.Helpers;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
 public class Decryptor {
        public static String decryptSecretKey(String encryptedString, String password) {
            try {
                byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
                SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
                return decryptedString;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void main(String[] args) {
            String encryptedString = "U2FsdGVkX1/RT7dFvAm3/Kki7D82fYDbdIwrjtIoJrX9uw4G7yIUP/vdxAwsPMvO";
            String password = "test1234";
            String decryptedString = decryptSecretKey(encryptedString, password);
            System.out.println("Decrypted String: " + decryptedString);
        }
    }

