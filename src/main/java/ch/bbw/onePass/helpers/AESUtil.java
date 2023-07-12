package ch.bbw.onePass.helpers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String ENCRYPT_SECRET_KEY = "J8^k$*FN5C86oxcB";
    private static final String ENCRYPT_INITIALIZATION_VECTOR = "7lLu*!f777H3585@";

    private static final String DECRYPT_SECRET_KEY = "7h2vfz*#RBi41M9v";
    private static final String DECRYPT_INITIALIZATION_VECTOR = "1lulL0vacj!Nox14";

    public static String encrypt(String value) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(ENCRYPT_INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPT_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(DECRYPT_INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(DECRYPT_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decrypted);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("b8yqBZOq0Zy96UcYf+YIzjJxYbFprk/O7K9Vp6C7/NZXdMU2M4l6V08cTfBTq7Rh"));
    }
}
