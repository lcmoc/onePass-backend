package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

}
