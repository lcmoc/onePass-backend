package ch.bbw.onePass.repository;

import ch.bbw.onePass.model.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

}