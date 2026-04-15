package poly.edu.asm_be.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import poly.edu.asm_be.dto.ApiResponse;
import poly.edu.asm_be.dto.CartDTO;
import poly.edu.asm_be.dto.CartItemDTO;
import poly.edu.asm_be.service.CartService;
import poly.edu.asm_be.service.CustomUserDetailsService;

import java.util.List;

@RestController("cartApiController")
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
    
    private final CartService cartService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getCart() {
        Long userId = getCurrentUserId();
        List<CartItemDTO> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(ApiResponse.success(cartItems));
    }
    
    @GetMapping("/details")
    public ResponseEntity<ApiResponse<CartDTO>> getCartDetails() {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDTO>> addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Product added to cart successfully", cart));
    }
    
    @PostMapping("/add-item")
    public ResponseEntity<ApiResponse<CartDTO>> addCartItem(@Valid @RequestBody CartItemDTO cartItemDTO) {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.addToCart(userId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Product added to cart successfully", cart));
    }
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartDTO>> updateCartItem(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.updateCartItem(userId, productId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Cart item updated successfully", cart));
    }
    
    @PutMapping("/update-item")
    public ResponseEntity<ApiResponse<CartDTO>> updateCartItemByBody(@Valid @RequestBody CartItemDTO cartItemDTO) {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.updateCartItem(userId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Cart item updated successfully", cart));
    }
    
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<CartDTO>> removeFromCart(@PathVariable Long productId) {
        Long userId = getCurrentUserId();
        CartDTO cart = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed from cart successfully", cart));
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        Long userId = getCurrentUserId();
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared successfully", null));
    }
    
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Integer>> getCartItemCount() {
        Long userId = getCurrentUserId();
        Integer totalItems = cartService.getTotalItems(userId);
        return ResponseEntity.ok(ApiResponse.success(totalItems));
    }
    
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        return principal.getUser().getId();
    }
}