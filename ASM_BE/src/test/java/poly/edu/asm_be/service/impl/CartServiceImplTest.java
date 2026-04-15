package poly.edu.asm_be.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CartService Unit Tests")
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User testUser;
    private Product testProduct;
    private Cart testCart;
    private CartItem testCartItem;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFullname("Test User");
        testUser.setRole(User.Role.ROLE_USER);

        // Setup test product
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setDescription("Test Description");
        testProduct.setImage("test-image.jpg");
        testProduct.setIsActive(true);

        // Setup test cart
        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);

        // Setup test cart item
        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setCart(testCart);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);
    }

    @Test
    @DisplayName("Should successfully add product to cart when product exists and is active")
    void addToCart_Success_WhenProductExistsAndActive() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Integer quantity = 2;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(testCartItem);
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList(testCartItem));

        // When
        CartDTO result = cartService.addToCart(userId, productId, quantity);

        // Then
        assertNotNull(result);
        assertEquals(testCart.getId(), result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(1, result.getCartItems().size());
        assertEquals(new BigDecimal("199.98"), result.getTotalAmount()); // 99.99 * 2
        assertEquals(2, result.getTotalItems());

        verify(productRepository).findById(productId);
        verify(cartItemRepository).save(any(CartItem.class));
        verify(cartItemRepository).findByCartId(testCart.getId());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when product does not exist")
    void addToCart_ThrowsException_WhenProductNotFound() {
        // Given
        Long userId = 1L;
        Long productId = 999L;
        Integer quantity = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> cartService.addToCart(userId, productId, quantity)
        );

        assertEquals("Product not found with id: " + productId, exception.getMessage());
        verify(productRepository).findById(productId);
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when product is not active")
    void addToCart_ThrowsException_WhenProductNotActive() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Integer quantity = 1;

        testProduct.setIsActive(false); // Make product inactive

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cartService.addToCart(userId, productId, quantity)
        );

        assertEquals("Product is not available", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    @DisplayName("Should successfully update existing cart item quantity")
    void addToCart_Success_WhenItemAlreadyExists() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Integer additionalQuantity = 3;
        Integer existingQuantity = 2;

        CartItem existingCartItem = new CartItem();
        existingCartItem.setId(1L);
        existingCartItem.setCart(testCart);
        existingCartItem.setProduct(testProduct);
        existingCartItem.setQuantity(existingQuantity);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(Optional.of(existingCartItem));
        when(cartItemRepository.save(existingCartItem)).thenReturn(existingCartItem);
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList(existingCartItem));

        // When
        CartDTO result = cartService.addToCart(userId, productId, additionalQuantity);

        // Then
        assertNotNull(result);
        assertEquals(5, existingCartItem.getQuantity()); // 2 + 3
        verify(cartItemRepository).save(existingCartItem);
        verify(cartItemRepository, never()).save(argThat(item -> item != existingCartItem));
    }

    @Test
    @DisplayName("Should successfully update cart item quantity")
    void updateCartItem_Success_WhenItemExists() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Integer newQuantity = 5;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(Optional.of(testCartItem));
        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList(testCartItem));

        // When
        CartDTO result = cartService.updateCartItem(userId, productId, newQuantity);

        // Then
        assertNotNull(result);
        assertEquals(newQuantity, testCartItem.getQuantity());
        verify(cartItemRepository).save(testCartItem);
        verify(cartItemRepository, never()).delete(any(CartItem.class));
    }

    @Test
    @DisplayName("Should delete cart item when quantity is zero or negative")
    void updateCartItem_Success_WhenQuantityIsZeroOrNegative() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        Integer zeroQuantity = 0;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(Optional.of(testCartItem));
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList());

        // When
        CartDTO result = cartService.updateCartItem(userId, productId, zeroQuantity);

        // Then
        assertNotNull(result);
        verify(cartItemRepository).delete(testCartItem);
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating non-existent cart item")
    void updateCartItem_ThrowsException_WhenItemNotFound() {
        // Given
        Long userId = 1L;
        Long productId = 999L;
        Integer quantity = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartIdAndProductId(testCart.getId(), productId))
                .thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> cartService.updateCartItem(userId, productId, quantity)
        );

        assertEquals("Cart item not found", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
        verify(cartItemRepository, never()).delete(any(CartItem.class));
    }

    @Test
    @DisplayName("Should successfully get cart by user ID")
    void getCartByUserId_Success_WhenUserExists() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList(testCartItem));

        // When
        CartDTO result = cartService.getCartByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(testCart.getId(), result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(1, result.getCartItems().size());

        CartItemDTO cartItemDTO = result.getCartItems().get(0);
        assertEquals(testCartItem.getId(), cartItemDTO.getId());
        assertEquals(testProduct.getId(), cartItemDTO.getProductId());
        assertEquals(testProduct.getName(), cartItemDTO.getProductName());
        assertEquals(testProduct.getPrice(), cartItemDTO.getPrice());
        assertEquals(testCartItem.getQuantity(), cartItemDTO.getQuantity());
        assertEquals(new BigDecimal("199.98"), cartItemDTO.getSubtotal()); // 99.99 * 2
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user does not exist")
    void getCartByUserId_ThrowsException_WhenUserNotFound() {
        // Given
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> cartService.getCartByUserId(userId)
        );

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(cartRepository, never()).findByUserId(anyLong());
    }

    @Test
    @DisplayName("Should create new cart when user exists but has no cart")
    void getCartByUserId_Success_CreatesNewCartWhenNotExists() {
        // Given
        Long userId = 1L;
        Cart newCart = new Cart();
        newCart.setId(2L);
        newCart.setUser(testUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);
        when(cartItemRepository.findByCartId(newCart.getId())).thenReturn(Arrays.asList());

        // When
        CartDTO result = cartService.getCartByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(newCart.getId(), result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(0, result.getCartItems().size());
        assertEquals(BigDecimal.ZERO, result.getTotalAmount());
        assertEquals(0, result.getTotalItems());

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Should successfully get total items count")
    void getTotalItems_Success_ReturnsCorrectCount() {
        // Given
        Long userId = 1L;
        CartItem item1 = new CartItem();
        item1.setQuantity(2);
        item1.setProduct(testProduct);
        item1.setCart(testCart);

        CartItem item2 = new CartItem();
        item2.setQuantity(3);
        item2.setProduct(testProduct);
        item2.setCart(testCart);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));
        when(cartItemRepository.findByCartId(testCart.getId())).thenReturn(Arrays.asList(item1, item2));

        // When
        Integer totalItems = cartService.getTotalItems(userId);

        // Then
        assertEquals(5, totalItems); // 2 + 3
    }

    @Test
    @DisplayName("Should successfully clear cart")
    void clearCart_Success_RemovesAllItems() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(testCart));

        // When
        cartService.clearCart(userId);

        // Then
        verify(cartItemRepository).deleteByCartId(testCart.getId());
    }
}