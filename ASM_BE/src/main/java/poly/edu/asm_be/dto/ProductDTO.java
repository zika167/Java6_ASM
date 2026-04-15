package poly.edu.asm_be.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;
    
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String image;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    private String categoryName;
    private Boolean isActive = true;
}