package ch.bbw.onePass.controller;

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

import java.util.List;
import java.util.Optional;

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

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.loadAll());
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/users/email={email}")
    public ResponseEntity<Optional<UserEntity>> getUserByEmail(@PathVariable String email) {
        Optional<UserEntity> user = userService.getByEmail(email);

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
