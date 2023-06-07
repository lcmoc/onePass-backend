package ch.bbw.onePass.service;

import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public List<CategoryEntity> loadAll() {
        return (List<CategoryEntity>) repository.findAll();
    }

    public Optional<CategoryEntity> loadOne(Long id) {
        return repository.findById(id);
    }

    public CategoryEntity create(CategoryEntity category) {
        return repository.save(category);
    }

    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    public Optional<CategoryEntity> getByName(String email) {
        return repository.findByName(email);
    }

}