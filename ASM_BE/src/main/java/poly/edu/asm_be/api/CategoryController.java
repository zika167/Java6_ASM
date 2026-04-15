package poly.edu.asm_be.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.asm_be.dto.ApiResponse;
import poly.edu.asm_be.dto.CategoryDTO;
import poly.edu.asm_be.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllActiveCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategoriesIncludingInactive() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success(category));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> searchCategories(@RequestParam String keyword) {
        List<CategoryDTO> categories = categoryService.searchCategories(keyword);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", createdCategory));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", updatedCategory));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }
}