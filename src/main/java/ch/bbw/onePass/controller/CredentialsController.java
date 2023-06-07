package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.model.UserEntity;
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

    @Autowired
    public  CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
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
    public ResponseEntity<CredentialsEntity>
    addCredential(@RequestBody CredentialsEntity credential) {

        credentialsService.create(credential);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(credential);
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
}
