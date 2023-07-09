package ch.bbw.onePass.repository;

import ch.bbw.onePass.model.CredentialsEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CredentialsRepository extends CrudRepository<CredentialsEntity, Long> {
    List<CredentialsEntity> findByCategoryUserId(Long userId);

    void deleteByCategory_User_Id(Long userId);

    void deleteByCategoryId(Long categoryId);
    List<CredentialsEntity> findByCategory_Id(Long categoryId);

}
