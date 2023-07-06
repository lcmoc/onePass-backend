package ch.bbw.onePass.service;

import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> loadAll() {
        return (List< UserEntity>) repository.findAll();
    }

    public Optional<UserEntity> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<UserEntity> loadOne(Long id) {
        return repository.findById(id);
    }

    public UserEntity create(UserEntity userEntity) {
        if (repository.existsByEmail(userEntity.getEmail())) {
            throw new RuntimeException("E-Mail already exists");
        }
        return repository.save(userEntity);
    }

    public UserEntity update(UserEntity updateUser) {
        return repository.save(updateUser);
    }

    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    public List<String> getAllEmails() {
        List<UserEntity> users = (List<UserEntity>) repository.findAll();
        List<String> emails = new ArrayList<>();

        for (UserEntity user : users) {
            emails.add(user.getEmail());
        }

        return emails;
    }
}
