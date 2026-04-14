package poly.edu.asm_be.service;

import poly.edu.asm_be.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllActiveCategories();
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    List<CategoryDTO> searchCategories(String keyword);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}