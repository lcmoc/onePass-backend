package ch.bbw.onePass.controller;

import ch.bbw.onePass.service.UserService;
import ch.bbw.onePass.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService studentService) {
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
}
