package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentialsService.loadAll());
    }

}
