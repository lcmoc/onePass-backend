package ch.bbw.onePass.Service;

import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> loadAll() {
        return (List< UserEntity>) repository.findAll();
    }

}
