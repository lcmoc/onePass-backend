package ch.bbw.onePass.controller;

import ch.bbw.onePass.JsonReturnModels.CategoryReturn;
import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.model.UserEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import ch.bbw.onePass.service.UserService;
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
    private final UserService userService;

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
    public CategoryController(CategoryService categoryService, CredentialsService credentialsService, UserService userService) {
        this.categoryService = categoryService;
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryEntity>
    addCategory(@RequestBody CategoryEntity category, @RequestParam("uuid") String frontendUuid) {
        Optional<UserEntity> user = userService.loadOne(category.getUser().getId());

        if (user.isPresent()) {
            if (user.get().getSessionUUID().equals(frontendUuid)) {
                categoryService.create(category);
                return ResponseEntity
                        .status(HttpStatus.CREATED)  // HTTP 201
                        .contentType(MediaType.APPLICATION_JSON)
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity>
    updateCategory(@RequestBody CategoryEntity category, @RequestParam("uuid") String frontendUuid) {
        Optional<UserEntity> user = userService.loadOne(category.getUser().getId());

        if (user.isPresent()) {
            if (user.get().getSessionUUID().equals(frontendUuid)) {
                categoryService.create(category);
                return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                        .contentType(MediaType.APPLICATION_JSON)
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?>
    deleteCategory(@PathVariable Long id, @RequestParam("uuid") String frontendUuid) {
        Optional<CategoryEntity> category = categoryService.loadOne(id);
        Optional<UserEntity> user = userService.loadOne(category.get().getUser_id());

        if (!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (user.get().getSessionUUID().equals(frontendUuid)) {
            List<CredentialsEntity> credentials = credentialsService.getCredentialsByCategoryId(category.get().getId());

            for (CredentialsEntity credential : credentials) {
                credentialsService.delete(credential.getId());
            }
            categoryService.delete(id);
            return ResponseEntity.ok().build();  // HTTP 204
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/categories/user/{userId}")
    public ResponseEntity<List<CategoryReturn>> getCategoriesByUserId(@PathVariable("userId") Long userId, @RequestParam("uuid") String frontendUuid) {
        Optional<UserEntity> user = userService.loadOne(userId);

        if (user.isPresent()) {
            if (user.get().getSessionUUID().equals(frontendUuid)) {
                if (user.get().getSessionUUID().equals(frontendUuid)) {

                    List<CategoryEntity> categories = (List<CategoryEntity>) categoryService.getCategoryByUserId(userId);
                    List<CategoryReturn> categoryReturnList = mapCategoriesToCategoriesReturnList(categories);

                    return ResponseEntity
                            .status(HttpStatus.OK) // HTTP 200
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(categoryReturnList);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/categories/name={name}")
    public ResponseEntity<Optional<CategoryEntity>> getCategoryByName(@PathVariable String name, @RequestParam("uuid") String frontendUuid) {
        Optional<CategoryEntity> category = categoryService.getByName(name);
        Optional<UserEntity> user = userService.loadOne(category.get().getUser().getId());

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        if(user.isPresent()) {
            if (user.get().getSessionUUID().equals(frontendUuid)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(category);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
