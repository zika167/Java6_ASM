package poly.edu.asm_be.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.asm_be.dto.CategoryDTO;
import poly.edu.asm_be.entity.Category;
import poly.edu.asm_be.repository.CategoryRepository;
import poly.edu.asm_be.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return convertToDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category name already exists: " + categoryDTO.getName());
        }
        
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // Check if name already exists (excluding current category)
        if (!existingCategory.getName().equals(categoryDTO.getName()) && 
            categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category name already exists: " + categoryDTO.getName());
        }

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setIsActive(categoryDTO.getIsActive());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> searchCategories(String keyword) {
        return categoryRepository.searchByName(keyword)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIsActive(category.getIsActive());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setIsActive(dto.getIsActive());
        return category;
    }
}