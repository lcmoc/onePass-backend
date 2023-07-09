package ch.bbw.onePass.service;

import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.repository.CredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<CredentialsEntity> loadOne(Long id) {
        return repository.findById(id);
    }

    public CredentialsEntity create(CredentialsEntity credential) {
        System.out.println(credential.getUser());
        return repository.save(credential);
    }

    public CredentialsEntity update(CredentialsEntity credential) {
        return repository.save(credential);
    }

    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    public List<CredentialsEntity> getAllCredentialsByUserId(Long userId) {
        return repository.findByCategoryUserId(userId);
    }

    public void deleteByUserId(Long userId) {
        repository.deleteByCategory_User_Id(userId);
    }

    public void deleteByCategoryId(Long categoryId) {
        repository.deleteByCategoryId(categoryId);
    }

    public List<CredentialsEntity> getCredentialsByCategoryId(Long categoryId) {
        return repository.findByCategory_Id(categoryId);
    }
}
