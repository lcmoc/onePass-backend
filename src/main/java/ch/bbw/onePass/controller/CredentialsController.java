package ch.bbw.onePass.controller;

import ch.bbw.onePass.JsonReturnModels.CredentialsReturn;
import ch.bbw.onePass.helpers.UUIDUtils;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CredentialsController {
    private final CredentialsService credentialsService;

    private List<CredentialsReturn> mapCredentialsToCredentialsReturnList(List<CredentialsEntity> credentials) {
        List<CredentialsReturn> credentialsReturnList = new ArrayList<>();
        for (CredentialsEntity credential : credentials) {
            CredentialsReturn credentialsReturn = new CredentialsReturn(
                    credential.getId(),
                    credential.getUsername(),
                    credential.getEmail(),
                    credential.getPassword(),
                    credential.getUrl(),
                    credential.getNotice(),
                    credential.getName(),
                    credential.getUser().getId(),
                    credential.getCategory().getId()
            );
            credentialsReturnList.add(credentialsReturn);
        }
        return credentialsReturnList;
    }

    @Autowired
    public CredentialsController(CredentialsService credentialsService, CategoryService categoryService) {
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
    public ResponseEntity<?> addCredential(@RequestBody CredentialsEntity credential) {

        if (credential != null) {
            credentialsService.create(credential);

            return ResponseEntity
                    .status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)  // HTTP 400
                    .build();
        }
    }

    // extra update for password?
    @PutMapping("/credentials/{id}")
    public ResponseEntity<CredentialsEntity>
    updateCredential(@RequestBody CredentialsEntity credential) {
        Optional<CredentialsEntity> existingCredential = credentialsService.loadOne(credential.getId());

        if (!existingCredential.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (existingCredential.equals(credential)) {
            // check if the equals works (doesn't seem like it)
            // implement correct response entity
            throw new RuntimeException("Nothing changed");
        }

        credentialsService.update(credential);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<?>
    deleteCredential(@PathVariable Long id) {
        Optional<CredentialsEntity> credential = credentialsService.loadOne(id);

        if (credential.isPresent()) {
            credentialsService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @GetMapping("/credentials/user/{userId}")
    public ResponseEntity<List<CredentialsReturn>> getCredentialsByUserId(@PathVariable("userId") Long userId, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        List<CredentialsEntity> credentials = credentialsService.getAllCredentialsByUserId(userId);
        List<CredentialsReturn> credentialsReturnList = mapCredentialsToCredentialsReturnList(credentials);

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(userId);

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(credentialsReturnList);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
