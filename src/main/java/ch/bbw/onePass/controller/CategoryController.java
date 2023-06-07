package ch.bbw.onePass.controller;

import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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

    @PostMapping("/categories")
    public ResponseEntity<CategoryEntity>
    addCategory(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(category);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity>
    updateUser(@RequestBody CategoryEntity category) {

        categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                .contentType(MediaType.APPLICATION_JSON)
                .body(category);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?>
    deleteUser(@PathVariable Long id) {
        Optional<CategoryEntity> category = categoryService.loadOne(id);

        if (category.isPresent()) {
            categoryService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        } else {
            return ResponseEntity.notFound().build();   // HTTP 404
        }
    }

}
