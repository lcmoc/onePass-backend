package ch.bbw.onePass.service;

import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.repository.CredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsRepository repository;

    public CredentialsService(CredentialsRepository repository) {
        this.repository = repository;
    }

    public List<CredentialsEntity> loadAll() {
        return (List<CredentialsEntity>) repository.findAll();
    }

    public CredentialsEntity getCredentialById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credential not found"));
    }

    public String getPasswordById(Long id) {
        CredentialsEntity credential = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credential not found"));

        return credential.getPassword();
    }
}
