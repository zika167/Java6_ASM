package poly.edu.asm_be.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.entity.Category;
import poly.edu.asm_be.entity.Product;
import poly.edu.asm_be.repository.CategoryRepository;
import poly.edu.asm_be.repository.ProductRepository;
import poly.edu.asm_be.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Override
    public List<ProductDTO> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<ProductDTO> getAllActiveProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable)
                .map(this::convertToDTO);
    }
    
    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }
    
    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
    
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImage(productDTO.getImage());
        existingProduct.setIsActive(productDTO.getIsActive());
        
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }
    
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setIsActive(product.getIsActive());
        
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        
        return dto;
    }
    
    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setIsActive(dto.getIsActive());
        
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }
        
        return product;
    }
}