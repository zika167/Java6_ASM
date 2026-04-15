package poly.edu.asm_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private String username;
    private List<CartItemDTO> cartItems;
    private BigDecimal totalAmount;
    private Integer totalItems;
}