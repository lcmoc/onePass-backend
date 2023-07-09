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

    @GetMapping("/credentials/{id}/password")
    public ResponseEntity<String> getPassword(@PathVariable("id") Long id, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        String password = credentialsService.getPasswordById(id);
        Optional<CredentialsEntity> credential = credentialsService.loadOne(id);

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(credential.get().getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(password);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/credentials")
    public ResponseEntity<?> addCredential(@RequestBody CredentialsEntity credential, @RequestParam("uuid") String frontendUuid, HttpSession session) {

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(credential.getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            credentialsService.create(credential);

            return ResponseEntity
                    .status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)  // HTTP 400
                    .build();
        }
    }

    // extra update for password?
    @PutMapping("/credentials/{id}")
    public ResponseEntity<CredentialsEntity>
    updateCredential(@RequestBody CredentialsEntity credential, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        Optional<CredentialsEntity> existingCredential = credentialsService.loadOne(credential.getId());

        if (!existingCredential.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (existingCredential.equals(credential)) {
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Nothing changed");
        }

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(credential.getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            credentialsService.update(credential);
            return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<?>
    deleteCredential(@PathVariable Long id, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        Optional<CredentialsEntity> credential = credentialsService.loadOne(id);

        if (!credential.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(credential.get().getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            credentialsService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
