package ch.bbw.onePass.controller;

import ch.bbw.onePass.helpers.UUIDUtils;
import ch.bbw.onePass.helpers.UserUuidDto;
import ch.bbw.onePass.helpers.UuidSingleton;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import ch.bbw.onePass.service.UserService;
import ch.bbw.onePass.model.UserEntity;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.loadAll());
    }

    @GetMapping("/users/email={email}")
    public ResponseEntity<UserUuidDto> loginUser(@PathVariable String email, HttpSession session) {
        Optional<UserEntity> optionalUser = userService.getByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UUID uuid = UuidSingleton.getInstance().getUuid();
        UserEntity user = optionalUser.get();
        UserUuidDto userUuidDto = new UserUuidDto(user, uuid);

        session.setAttribute("uuid", uuid.toString());
        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok(userUuidDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
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
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        String sessionUuidString = (String) session.getAttribute("uuid");

        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(user.getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            Optional<UserEntity> existingUser = userService.loadOne(user.getId());

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
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(id);

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            Optional<UserEntity> user = userService.loadOne(id);

            if (user.isPresent()) {
                //TODO: delete all
                credentialsService.deleteByUserId(id);
                categoryService.deleteByUserId(id);
                userService.delete(id);
                return ResponseEntity.noContent().build(); // HTTP 204
            } else {
                return ResponseEntity.notFound().build(); // HTTP 404
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // HTTP 401
    }

    @GetMapping("users/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = userService.getAllEmails();
        return ResponseEntity.ok(emails);
    }
}
