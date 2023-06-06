package ch.bbw.onePass.service;

import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.repository.UserRepository;
import org.springframework.stereotype.Service;

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

}
