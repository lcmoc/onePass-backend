package ch.bbw.onePass.controller;

import ch.bbw.onePass.helpers.AESUtil;
import ch.bbw.onePass.JsonReturnModels.UserUuidDto;
import ch.bbw.onePass.helpers.UuidSingleton;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import ch.bbw.onePass.service.UserService;
import ch.bbw.onePass.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/users/email={email}")
    public ResponseEntity<?> loginUser(@PathVariable String email, @RequestParam("uuid") String frontendUuid) throws Exception {
        Optional<UserEntity> existingUser = userService.getByEmail(email);

        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UUID uuid = UuidSingleton.getInstance().getUuid();
        String encryptedUUID = AESUtil.encrypt(uuid.toString());

        if(frontendUuid.isEmpty()) {
            existingUser.get().setSessionUUID(uuid.toString());
            return ResponseEntity.ok(encryptedUUID);
        }

        if (existingUser.get().getSessionUUID().equals(AESUtil.decrypt(frontendUuid))) {
            UserEntity user = existingUser.get();
            UserUuidDto userUuidDto = new UserUuidDto(user.getId(), user.getSecretKey(), user.getEmail(), encryptedUUID);
            return ResponseEntity.ok(userUuidDto);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<?> logoutUser(@PathVariable Long id) {
        Optional<UserEntity> user = userService.loadOne(id);

        if(user.isPresent()) {
            user.get().setSessionUUID(null);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {

        String userEmail = user.getEmail();
        Optional<UserEntity> optionalUser = userService.getByEmail(userEmail);

        if(optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Email already exists");
        }

        userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED) // HTTP 201 - Erstellt
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @PutMapping("/users")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user, @RequestParam("uuid") String frontendUuid) throws Exception {
        Optional<UserEntity> existingUser = userService.loadOne(user.getId());

        if (existingUser.get().getSessionUUID().equals(AESUtil.decrypt(frontendUuid))) {

                if (!existingUser.isPresent()) {
                    return ResponseEntity.notFound().build();
                }

                if (existingUser.get().equals(user)) {
                    ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Nothing changed");
                }

                userService.update(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam("uuid") String frontendUuid) throws Exception {
        Optional<UserEntity> existingUser = userService.loadOne(id);

        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }

        if (existingUser.get().getSessionUUID().equals(AESUtil.decrypt(frontendUuid))) {
                credentialsService.deleteByUserId(id);
                categoryService.deleteByUserId(id);
                userService.delete(id);
                return ResponseEntity.ok().build(); // HTTP 204
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401
    }
}
