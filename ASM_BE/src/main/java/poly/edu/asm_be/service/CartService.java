package poly.edu.asm_be.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.asm_be.dto.CartDTO;
import poly.edu.asm_be.dto.CartItemDTO;
import poly.edu.asm_be.entity.Cart;
import poly.edu.asm_be.entity.CartItem;
import poly.edu.asm_be.entity.Product;
import poly.edu.asm_be.entity.User;
import poly.edu.asm_be.repository.CartItemRepository;
import poly.edu.asm_be.repository.CartRepository;
import poly.edu.asm_be.repository.ProductRepository;
import poly.edu.asm_be.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartDTO getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return convertToCartDTO(cart);
    }

    public CartDTO addToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        if (!product.getIsActive()) {
            throw new RuntimeException("Product is not available");
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()) {
            // Update existing item
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Create new item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return convertToCartDTO(cart);
    }

    public CartDTO updateCartItem(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return convertToCartDTO(cart);
    }

    public CartDTO removeFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        return convertToCartDTO(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    public List<CartItemDTO> getCartItems(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        return cartItems.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
    }

    public Integer getTotalItems(Long userId) {
        List<CartItemDTO> items = getCartItems(userId);
        return items.stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();
    }

    private Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    private CartDTO convertToCartDTO(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());

        BigDecimal totalAmount = cartItemDTOs.stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItems = cartItemDTOs.stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setUsername(cart.getUser().getUsername());
        cartDTO.setCartItems(cartItemDTOs);
        cartDTO.setTotalAmount(totalAmount);
        cartDTO.setTotalItems(totalItems);

        return cartDTO;
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        BigDecimal subtotal = cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setProductImage(cartItem.getProduct().getImage());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setSubtotal(subtotal);

        return dto;
    }
}