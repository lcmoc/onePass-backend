package ch.bbw.onePass.service;

import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.repository.CategoryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public List<CategoryEntity> loadAll() {
        return (List<CategoryEntity>) repository.findAll();
    }

}