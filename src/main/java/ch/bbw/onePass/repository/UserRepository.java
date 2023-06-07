package ch.bbw.onePass.repository;

import ch.bbw.onePass.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String findByEmail);
    boolean existsByEmail(String email);
}
