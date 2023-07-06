package ch.bbw.onePass.controller;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import ch.bbw.onePass.helpers.UserUuidDto;
import ch.bbw.onePass.helpers.UuidSingleton;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import ch.bbw.onePass.service.UserService;
import ch.bbw.onePass.model.UserEntity;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Controller
public class UserController {
    private final CredentialsService credentialsService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public UserController(CredentialsService credentialsService, CategoryService categoryService, UserService studentService) {
        this.credentialsService = credentialsService;
        this.categoryService = categoryService;
        this.userService = studentService;
    }

    @PostMapping("/allUsers")
    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.loadAll());
    }

    public static boolean decryptSecretKey(String password, String encryptedSecretKey) {
        try {
            byte[] key = password.getBytes("UTF-8");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            byte[] encryptedBytes = Base64.decodeBase64(encryptedSecretKey);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedSecretKey = new String(decryptedBytes, "UTF-8");
            System.out.println("Decrypted Secret Key: " + decryptedSecretKey);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to decrypt: " + e);
            return false;
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @PostMapping("/users/email={email}")
    public ResponseEntity<UserUuidDto> loginUser(@PathVariable String email) {
        Optional<UserEntity> optionalUser = userService.getByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UUID uuid = UuidSingleton.getInstance().getUuid();
        UserEntity user = optionalUser.get();
        UserUuidDto userUuidDto = new UserUuidDto(user, uuid);

        return ResponseEntity.ok(userUuidDto);
    }


    @CrossOrigin(origins = {"http://localhost:3000/"})
    @PostMapping("/users")
    public ResponseEntity<UserEntity>
    addUser(@RequestBody UserEntity user) {

        userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @PutMapping("/users")
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


    @CrossOrigin(origins = {"http://localhost:3000/"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?>
    deleteUser(@PathVariable Long id) {
        Optional<UserEntity> user = userService.loadOne(id);

        if (user.isPresent()) {
            credentialsService.deleteByUserId(id);
            categoryService.deleteByUserId(id);
            userService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("users/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = userService.getAllEmails();
        return ResponseEntity.ok(emails);
    }
}
