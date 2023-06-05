package ch.bbw.onePass.controller;

import ch.bbw.onePass.Service.UserService;
import ch.bbw.onePass.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
}
