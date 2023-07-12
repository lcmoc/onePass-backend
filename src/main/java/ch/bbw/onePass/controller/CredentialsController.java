package ch.bbw.onePass.controller;

import ch.bbw.onePass.JsonReturnModels.CategoryReturn;
import ch.bbw.onePass.JsonReturnModels.CredentialsReturn;
import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.service.CredentialsService;
import ch.bbw.onePass.service.UserService;
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
    private final UserService userService;


    @Autowired
    public CredentialsController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    private List<CredentialsReturn> mapCredentialsToCredentialsReturnList(List<CredentialsEntity> credentials) {
        List<CredentialsReturn> credentialsReturnList = new ArrayList<>();
        for (CredentialsEntity credential : credentials) {
            CategoryEntity categoryEntity = credential.getCategory();
            CategoryReturn categoryReturn = new CategoryReturn(
                    categoryEntity.getId(),
                    categoryEntity.getName(),
                    categoryEntity.getUser().getId()
            );

            CredentialsReturn credentialsReturn = new CredentialsReturn(
                    credential.getId(),
                    credential.getUsername(),
                    credential.getEmail(),
                    credential.getPassword(),
                    credential.getUrl(),
                    credential.getNotice(),
                    credential.getName(),
                    credential.getUser().getId(),
                    categoryReturn
            );
            credentialsReturnList.add(credentialsReturn);
        }
        return credentialsReturnList;
    }

    @PostMapping("/credentials")
    public ResponseEntity<?> addCredential(@RequestBody CredentialsEntity credential, @RequestParam("uuid") String frontendUuid) {
        Optional<UserEntity> user = userService.loadOne(credential.getUser().getId());

        if(user.isPresent()) {
            if (user.get().getSessionUUID().equals(frontendUuid)) {
                credentialsService.create(credential);

                return ResponseEntity
                        .status(HttpStatus.CREATED)  // HTTP 201
                        .contentType(MediaType.APPLICATION_JSON)
                        .build();
            }
        }

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)  // HTTP 400
            .build();

    }

    @PutMapping("/credentials/{id}")
    public ResponseEntity<CredentialsEntity>
    updateCredential(@RequestBody CredentialsEntity credential, @RequestParam("uuid") String frontendUuid) {
        Optional<CredentialsEntity> existingCredential = credentialsService.loadOne(credential.getId());
        Optional<UserEntity> user = userService.loadOne(credential.getUser().getId());

        if (!existingCredential.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (existingCredential.equals(credential)) {
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Nothing changed");
        }

        if(user.get().getSessionUUID().equals(frontendUuid)) {
            credentialsService.update(credential);
            return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<?>
    deleteCredential(@PathVariable Long id, @RequestParam("uuid") String frontendUuid) {
        Optional<CredentialsEntity> credential = credentialsService.loadOne(id);
        Optional<UserEntity> user = userService.loadOne(credential.get().getUser().getId());

        if (!credential.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if(user.get().getSessionUUID().equals(frontendUuid)) {
            credentialsService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/credentials/user/{userId}")
    public ResponseEntity<List<CredentialsReturn>> getCredentialsByUserId(@PathVariable("userId") Long userId, @RequestParam("uuid") String frontendUuid) {
        Optional<UserEntity> user = userService.loadOne(userId);

        if(user.isPresent()) {
            if(user.get().getSessionUUID().equals(frontendUuid)) {
                List<CredentialsEntity> credentials = credentialsService.getAllCredentialsByUserId(userId);
                List<CredentialsReturn> credentialsReturnList = mapCredentialsToCredentialsReturnList(credentials);
                return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(credentialsReturnList);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
