package poly.edu.asm_be.service;

import poly.edu.asm_be.dto.CartDTO;
import poly.edu.asm_be.dto.CartItemDTO;

import java.util.List;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addToCart(Long userId, Long productId, Integer quantity);
    CartDTO updateCartItem(Long userId, Long productId, Integer quantity);
    CartDTO removeFromCart(Long userId, Long productId);
    void clearCart(Long userId);
    List<CartItemDTO> getCartItems(Long userId);
    Integer getTotalItems(Long userId);
}