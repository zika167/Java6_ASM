package poly.edu.asm_be.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import poly.edu.asm_be.dto.ApiResponse;
import poly.edu.asm_be.dto.CartItemDTO;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.service.CustomUserDetailsService;
import poly.edu.asm_be.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CartController {
    
    private final ProductService productService;
    
    // Temporary in-memory cart storage (in production, use Redis or database)
    private final Map<String, List<CartItemDTO>> userCarts = new HashMap<>();
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getCart() {
        String userId = getCurrentUserId();
        List<CartItemDTO> cart = userCarts.getOrDefault(userId, new ArrayList<>());
        return ResponseEntity.ok(ApiResponse.success(cart));
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        try {
            String userId = getCurrentUserId();
            ProductDTO product = productService.getProductById(productId);
            
            List<CartItemDTO> cart = userCarts.getOrDefault(userId, new ArrayList<>());
            
            // Check if product already exists in cart
            CartItemDTO existingItem = cart.stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst()
                    .orElse(null);
            
            if (existingItem != null) {
                // Update quantity
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                existingItem.setSubtotal(existingItem.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())));
            } else {
                // Add new item
                CartItemDTO newItem = new CartItemDTO();
                newItem.setProductId(product.getId());
                newItem.setProductName(product.getName());
                newItem.setProductImage(product.getImage());
                newItem.setPrice(product.getPrice());
                newItem.setQuantity(quantity);
                newItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                cart.add(newItem);
            }
            
            userCarts.put(userId, cart);
            return ResponseEntity.ok(ApiResponse.success("Product added to cart", cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> updateCartItem(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            String userId = getCurrentUserId();
            List<CartItemDTO> cart = userCarts.getOrDefault(userId, new ArrayList<>());
            
            CartItemDTO item = cart.stream()
                    .filter(cartItem -> cartItem.getProductId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found in cart"));
            
            if (quantity <= 0) {
                cart.remove(item);
            } else {
                item.setQuantity(quantity);
                item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }
            
            userCarts.put(userId, cart);
            return ResponseEntity.ok(ApiResponse.success("Cart updated", cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }
    
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> removeFromCart(@PathVariable Long productId) {
        try {
            String userId = getCurrentUserId();
            List<CartItemDTO> cart = userCarts.getOrDefault(userId, new ArrayList<>());
            cart.removeIf(item -> item.getProductId().equals(productId));
            
            userCarts.put(userId, cart);
            return ResponseEntity.ok(ApiResponse.success("Product removed from cart", cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        String userId = getCurrentUserId();
        userCarts.put(userId, new ArrayList<>());
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
    
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getCartTotal() {
        String userId = getCurrentUserId();
        List<CartItemDTO> cart = userCarts.getOrDefault(userId, new ArrayList<>());
        BigDecimal total = cart.stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return ResponseEntity.ok(ApiResponse.success(total));
    }
    
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        return principal.getUser().getId().toString();
    }
}