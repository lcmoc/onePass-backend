package ch.bbw.onePass.controller;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import ch.bbw.onePass.service.UserService;
import ch.bbw.onePass.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService studentService) {
        this.userService = studentService;
    }

    @PostMapping("/allUsers")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.loadAll());
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final Integer LENGTH = 128;
    public static String decrypt(String ciphertext) throws Exception {
        SecretKey secretKey = getSecretKey("o9szYIOq1rRMiouNhNvaq96lqUvCekxR");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
        //return new String(cipher.doFinal(base64Decode("ASDASDADS")));
    }

    public static SecretKey getSecretKey(String secretKey) throws Exception {
        byte[] decodeSecretKey = Base64.getDecoder().decode(secretKey);
        //byte[] decodeSecretKey = base64Decode(secretKey);
        return new SecretKeySpec(decodeSecretKey, 0, decodeSecretKey.length, "AES");
    }
    
    @PostMapping("/users/email={email}&password={password}")
    public ResponseEntity<Optional<UserEntity>> getUserByEmail(@PathVariable String email, @PathVariable String password) throws UnsupportedEncodingException {
        Optional<UserEntity> user = userService.getByEmail(email);

        if(password != "") {
            String encryptedUserSecretKey = user.get().getSecretKey();


            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user);
        }

        user.get().setSecretKey("");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity>
    addUser(@RequestBody UserEntity user) {

        userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity>
    updateUser(@RequestBody UserEntity user) {
        Optional<UserEntity> existingUser = userService.loadOne(user.getId());

        if(!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if(existingUser.equals(user)) {
            // implement correct response entity
            throw new RuntimeException("Nothing changed");
        }

        userService.update(user);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?>
    deleteUser(@PathVariable Long id) {
        Optional<UserEntity> user = userService.loadOne(id);

        if (user.isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @PostMapping("users/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = userService.getAllEmails();
        return ResponseEntity.ok(emails);
    }
}
