package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class CategoryController {
    private final CategoryService categoryService;
    private final CredentialsService credentialsService;

    @Autowired
    public CategoryController(CategoryService categoryService, CredentialsService credentialsService) {
        this.categoryService = categoryService;
        this.credentialsService = credentialsService;
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> getCategories() {
        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.loadAll());
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
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

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/categories/{id}")
    public ResponseEntity<Optional<CategoryEntity>> getCategoryByID(@PathVariable Long id) {
        CategoryEntity user = categoryService.getCategoryById(id);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Optional.of(user));
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @PostMapping("/categories")
    public ResponseEntity<CategoryEntity>
    addCategory(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity>
    updateCategory(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @CrossOrigin(origins = {"http://localhost:3000/"})
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

    @CrossOrigin(origins = {"http://localhost:3000/"})
    @GetMapping("/categories/user/{userId}")
    public ResponseEntity<List<CategoryEntity>> getCategoriesByUserId(@PathVariable("userId") int userId) {
        List<CategoryEntity> categories = (List<CategoryEntity>) categoryService.getCategoryByUserId(userId);

        return ResponseEntity
                .status(HttpStatus.OK) // HTTP 200
                .contentType(MediaType.APPLICATION_JSON)
                .body(categories);
    }

}
