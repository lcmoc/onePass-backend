package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CredentialsController {
    private final CredentialsService credentialsService;
    private final CategoryService categoryService;

    @Autowired
    public  CredentialsController(CredentialsService credentialsService, CategoryService categoryService) {
        this.credentialsService = credentialsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/credentials")
    public ResponseEntity<List<CredentialsEntity>> getCredentials() {
        List<CredentialsEntity> credentials = credentialsService.loadAll();

        for (CredentialsEntity credential : credentials) {
            credential.setPassword(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials);
    }

    @GetMapping("/credentials/{id}")
    public ResponseEntity<CredentialsEntity> getCredentialById(@PathVariable Long id) {
        CredentialsEntity credential = credentialsService.getCredentialById(id);
        if (credential == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        credential.setPassword(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(credential);
    }

    @GetMapping("/credentials/{id}/password")
    public ResponseEntity<String> getPassword(@PathVariable("id") Long id) {
        String password = credentialsService.getPasswordById(id);

        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.TEXT_PLAIN)
                .body(password);
    }

    @PostMapping("/credentials")
    public ResponseEntity<?> addCredential(@RequestBody CredentialsEntity credential) {
        Long categoryId = credential.getCategory().getId();
        CategoryEntity category = categoryService.getCategoryById(categoryId);

        if (category == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)  // HTTP 404
                    .body("Category not found");
        }

        Long userId = credential.getUser().getId();
        Long categoryUserId = category.getUser_id();

        boolean areIdsNotEmpty = userId != null && categoryUserId != null;
        boolean areUserIdsEqual = userId.equals(categoryUserId);

        if (areIdsNotEmpty && areUserIdsEqual) {
            credentialsService.create(credential);
            return ResponseEntity
                    .status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(credential);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)  // HTTP 400
                    .build();
        }
    }

    @PutMapping("/credentials/{id}")
    public ResponseEntity<CredentialsEntity>
    updateCredential(@RequestBody CredentialsEntity credential) {

        credentialsService.update(credential);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(credential);
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<?>
    deleteUser(@PathVariable Long id) {
        Optional<CredentialsEntity> user = credentialsService.loadOne(id);

        if (user.isPresent()) {
            credentialsService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @GetMapping("/credentials/user/{userId}")
    public ResponseEntity<List<CredentialsEntity>> getCredentialsByUserId(@PathVariable("userId") int userId) {
        List<CredentialsEntity> credentials = (List<CredentialsEntity>) credentialsService.getCredentialsByUserId(userId);

        for (CredentialsEntity credential : credentials) {
            credential.setPassword(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials);
    }
}
