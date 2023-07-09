package ch.bbw.onePass.controller;

import ch.bbw.onePass.JsonReturnModels.CategoryReturn;
import ch.bbw.onePass.helpers.UUIDUtils;
import ch.bbw.onePass.model.CategoryEntity;
import ch.bbw.onePass.model.CredentialsEntity;
import ch.bbw.onePass.service.CategoryService;
import ch.bbw.onePass.service.CredentialsService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/categories")
    public ResponseEntity<CategoryEntity>
    addCategory(@RequestBody CategoryEntity category, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(category.getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            categoryService.create(category);
            return ResponseEntity
                    .status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity>
    updateCategory(@RequestBody CategoryEntity category, @RequestParam("uuid") String frontendUuid, HttpSession session) {

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(category.getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED)  // HTTP 201
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?>
    deleteCategory(@PathVariable Long id, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        Optional<CategoryEntity> category = categoryService.loadOne(id);
        List<CredentialsEntity> credentials = credentialsService.getCredentialsByCategoryId(category.get().getId());

        if (!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(category.get().getUser().getId());

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            for (CredentialsEntity credential : credentials) {
                credentialsService.delete(credential.getId());
            }
            categoryService.delete(id);
            return ResponseEntity.noContent().build();  // HTTP 204
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/categories/user/{userId}")
    public ResponseEntity<List<CategoryReturn>> getCategoriesByUserId(@PathVariable("userId") Long userId, @RequestParam("uuid") String frontendUuid, HttpSession session) {
        List<CategoryEntity> categories = (List<CategoryEntity>) categoryService.getCategoryByUserId(userId);
        List<CategoryReturn> categoryReturnList = mapCategoriesToCategoriesReturnList(categories);

        String sessionUuidString = (String) session.getAttribute("uuid");
        Long sessionUserId = (Long) session.getAttribute("userId");
        boolean userIdsAreEqual = sessionUserId.equals(userId);

        if (sessionUuidString != null && sessionUserId != null && UUIDUtils.compareUUIDs(frontendUuid, sessionUuidString) && userIdsAreEqual) {
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoryReturnList);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
