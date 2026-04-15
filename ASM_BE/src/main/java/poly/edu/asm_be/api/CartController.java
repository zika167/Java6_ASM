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
        try {
            Long userId = getCurrentUserId();
            System.out.println("=== Getting cart details for user: " + userId + " ===");
            CartDTO cart = cartService.getCartByUserId(userId);
            System.out.println("Cart items count: " + (cart.getCartItems() != null ? cart.getCartItems().size() : 0));
            return ResponseEntity.ok(ApiResponse.success(cart));
        } catch (Exception e) {
            System.err.println("Error getting cart details: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(ApiResponse.error(500, e.getMessage()));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDTO>> addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        try {
            Long userId = getCurrentUserId();
            System.out.println("=== Adding to cart: userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + " ===");
            CartDTO cart = cartService.addToCart(userId, productId, quantity);
            System.out.println("Cart updated successfully. Total items: " + cart.getTotalItems());
            return ResponseEntity.ok(ApiResponse.success("Product added to cart successfully", cart));
        } catch (Exception e) {
            System.err.println("Error adding to cart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(ApiResponse.error(500, e.getMessage()));
        }
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
        try {
            Long userId = getCurrentUserId();
            System.out.println("=== Getting cart count for user: " + userId + " ===");
            Integer totalItems = cartService.getTotalItems(userId);
            System.out.println("Cart count: " + totalItems);
            return ResponseEntity.ok(ApiResponse.success(totalItems));
        } catch (Exception e) {
            System.err.println("Error getting cart count: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(ApiResponse.success(0));
        }
    }
    
    private Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                System.err.println("User not authenticated");
                throw new RuntimeException("User not authenticated");
            }
            
            Object principal = authentication.getPrincipal();
            System.out.println("Principal type: " + principal.getClass().getName());
            
            if (principal instanceof CustomUserDetailsService.CustomUserPrincipal) {
                CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
                    (CustomUserDetailsService.CustomUserPrincipal) principal;
                Long userId = userPrincipal.getUser().getId();
                System.out.println("Current user ID: " + userId);
                return userId;
            } else {
                System.err.println("Principal is not CustomUserPrincipal: " + principal);
                throw new RuntimeException("Invalid user principal");
            }
        } catch (Exception e) {
            System.err.println("Error getting current user ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("User not authenticated");
        }
    }
}