package poly.edu.asm_be.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.asm_be.dto.ApiResponse;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Product Management", description = "APIs for managing products in the e-commerce system")
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    @Operation(
        summary = "Get all active products",
        description = "Retrieves a list of all active products in the system"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved products",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllActiveProducts();
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getProductsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getAllActiveProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProducts(@RequestParam String keyword) {
        List<ProductDTO> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<ProductDTO> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(ApiResponse.success(products));
    }
    
    @PostMapping
    @Operation(
        summary = "Create a new product",
        description = "Creates a new product in the system. Requires admin privileges.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Product created successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid input data"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401", 
            description = "Unauthorized - Authentication required"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403", 
            description = "Forbidden - Admin privileges required"
        )
    })
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(
            @Parameter(description = "Product data to create", required = true)
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(ApiResponse.success("Product created successfully", createdProduct));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }
}