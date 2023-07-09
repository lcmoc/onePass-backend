package ch.bbw.onePass.controller;

import ch.bbw.onePass.JsonReturnModels.CategoryReturn;
import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class CategoryController {
    private final CategoryService categoryService;
    private final CredentialsService credentialsService;

    private List<CategoryReturn> mapCategoriesToCategoriesReturnList(List<CategoryEntity> categories) {
        List<CategoryReturn> categoryReturnList = new ArrayList<>();
        for (CategoryEntity category : categories) {
            CategoryReturn categoryReturn = new CategoryReturn(
                    category.getId(),
                    category.getName(),
                    category.getUser_id()
            );
            categoryReturnList.add(categoryReturn);
        }
        return categoryReturnList;
    }

    @Autowired
    public CategoryController(CategoryService categoryService, CredentialsService credentialsService) {
        this.categoryService = categoryService;
        this.credentialsService = credentialsService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> getCategories() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.loadAll());
    }

    @GetMapping("/categories/name={name}")
    public ResponseEntity<Optional<CategoryEntity>> getCategoryByName(@PathVariable String name) {
        Optional<CategoryEntity> user = categoryService.getByName(name);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Optional<CategoryEntity>> getCategoryByID(@PathVariable Long id) {
        CategoryEntity category = categoryService.getCategoryById(id);

        if (category == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Optional.of(category));
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryEntity>
    addCategory(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity>
    updateCategory(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?>
    deleteCategory(@PathVariable Long id) {
        Optional<CategoryEntity> category = categoryService.loadOne(id);
        List<CredentialsEntity> credentials = credentialsService.getCredentialsByCategoryId(category.get().getId());

        if (category.isPresent()) {
            for (CredentialsEntity credential : credentials) {
                credentialsService.delete(credential.getId());
            }
            categoryService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

    @GetMapping("/categories/user/{userId}")
    public ResponseEntity<List<CategoryReturn>> getCategoriesByUserId(@PathVariable("userId") int userId) {
        List<CategoryEntity> categories = (List<CategoryEntity>) categoryService.getCategoryByUserId(userId);
        List<CategoryReturn> categoryReturnList = mapCategoriesToCategoriesReturnList(categories);

        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryReturnList);
    }

}
