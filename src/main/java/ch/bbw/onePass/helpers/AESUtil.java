package ch.bbw.onePass.helpers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY = "J8^k$*FN5C86oxcB";
    private static final String INITIALIZATION_VECTOR = "7lLu*!f777H3585@";

    public static String encrypt(String value) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
