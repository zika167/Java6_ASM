package poly.edu.asm_be.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.entity.Category;
import poly.edu.asm_be.entity.Product;
import poly.edu.asm_be.repository.CategoryRepository;
import poly.edu.asm_be.repository.ProductRepository;
import poly.edu.asm_be.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Category testCategory;
    private ProductDTO testProductDTO;

    @BeforeEach
    void setUp() {
        // Setup test category
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Electronics");
        testCategory.setIsActive(true);

        // Setup test product
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setDescription("Test Description");
        testProduct.setImage("test-image.jpg");
        testProduct.setCategory(testCategory);
        testProduct.setIsActive(true);

        // Setup test ProductDTO
        testProductDTO = new ProductDTO();
        testProductDTO.setId(1L);
        testProductDTO.setName("Test Product");
        testProductDTO.setPrice(new BigDecimal("99.99"));
        testProductDTO.setDescription("Test Description");
        testProductDTO.setImage("test-image.jpg");
        testProductDTO.setCategoryId(1L);
        testProductDTO.setCategoryName("Electronics");
        testProductDTO.setIsActive(true);
    }

    @Test
    @DisplayName("Should successfully get product by ID when product exists")
    void getProductById_Success_WhenProductExists() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        // When
        ProductDTO result = productService.getProductById(productId);

        // Then
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        assertEquals(testProduct.getPrice(), result.getPrice());
        assertEquals(testProduct.getDescription(), result.getDescription());
        assertEquals(testProduct.getImage(), result.getImage());
        assertEquals(testProduct.getCategory().getId(), result.getCategoryId());
        assertEquals(testProduct.getCategory().getName(), result.getCategoryName());
        assertEquals(testProduct.getIsActive(), result.getIsActive());

        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when product does not exist")
    void getProductById_ThrowsException_WhenProductNotFound() {
        // Given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals("Product not found with id: " + productId, exception.getMessage());
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("Should successfully get all active products")
    void getAllActiveProducts_Success_ReturnsOnlyActiveProducts() {
        // Given
        Product activeProduct1 = new Product();
        activeProduct1.setId(1L);
        activeProduct1.setName("Active Product 1");
        activeProduct1.setPrice(new BigDecimal("50.00"));
        activeProduct1.setCategory(testCategory);
        activeProduct1.setIsActive(true);

        Product activeProduct2 = new Product();
        activeProduct2.setId(2L);
        activeProduct2.setName("Active Product 2");
        activeProduct2.setPrice(new BigDecimal("75.00"));
        activeProduct2.setCategory(testCategory);
        activeProduct2.setIsActive(true);

        List<Product> activeProducts = Arrays.asList(activeProduct1, activeProduct2);
        when(productRepository.findByIsActiveTrue()).thenReturn(activeProducts);

        // When
        List<ProductDTO> result = productService.getAllActiveProducts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        
        ProductDTO dto1 = result.get(0);
        assertEquals(activeProduct1.getId(), dto1.getId());
        assertEquals(activeProduct1.getName(), dto1.getName());
        assertTrue(dto1.getIsActive());

        ProductDTO dto2 = result.get(1);
        assertEquals(activeProduct2.getId(), dto2.getId());
        assertEquals(activeProduct2.getName(), dto2.getName());
        assertTrue(dto2.getIsActive());

        verify(productRepository).findByIsActiveTrue();
    }

    @Test
    @DisplayName("Should successfully create product when category exists")
    void createProduct_Success_WhenCategoryExists() {
        // Given
        when(categoryRepository.findById(testProductDTO.getCategoryId())).thenReturn(Optional.of(testCategory));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        ProductDTO result = productService.createProduct(testProductDTO);

        // Then
        assertNotNull(result);
        assertEquals(testProductDTO.getName(), result.getName());
        assertEquals(testProductDTO.getPrice(), result.getPrice());
        assertEquals(testProductDTO.getDescription(), result.getDescription());
        assertEquals(testProductDTO.getCategoryId(), result.getCategoryId());

        verify(categoryRepository).findById(testProductDTO.getCategoryId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when creating product with non-existent category")
    void createProduct_ThrowsException_WhenCategoryNotFound() {
        // Given
        Long categoryId = 999L;
        testProductDTO.setCategoryId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> productService.createProduct(testProductDTO)
        );

        assertEquals("Category not found with id: " + categoryId, exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should successfully search products by keyword")
    void searchProducts_Success_ReturnsMatchingProducts() {
        // Given
        String keyword = "test";
        List<Product> searchResults = Arrays.asList(testProduct);
        when(productRepository.searchProducts(keyword)).thenReturn(searchResults);

        // When
        List<ProductDTO> result = productService.searchProducts(keyword);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getId(), result.get(0).getId());
        assertEquals(testProduct.getName(), result.get(0).getName());

        verify(productRepository).searchProducts(keyword);
    }

    @Test
    @DisplayName("Should successfully get products by price range")
    void getProductsByPriceRange_Success_ReturnsProductsInRange() {
        // Given
        BigDecimal minPrice = new BigDecimal("50.00");
        BigDecimal maxPrice = new BigDecimal("150.00");
        List<Product> productsInRange = Arrays.asList(testProduct);
        when(productRepository.findByPriceRange(minPrice, maxPrice))
                .thenReturn(productsInRange);

        // When
        List<ProductDTO> result = productService.getProductsByPriceRange(minPrice, maxPrice);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct.getId(), result.get(0).getId());
        assertTrue(result.get(0).getPrice().compareTo(minPrice) >= 0);
        assertTrue(result.get(0).getPrice().compareTo(maxPrice) <= 0);

        verify(productRepository).findByPriceRange(minPrice, maxPrice);
    }
}