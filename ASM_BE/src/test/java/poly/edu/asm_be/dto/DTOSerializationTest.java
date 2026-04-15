package poly.edu.asm_be.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import poly.edu.asm_be.entity.Order;
import poly.edu.asm_be.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify DTOs can be serialized to JSON without infinite recursion
 */
class DTOSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void testCartDTOSerialization() throws Exception {
        // Given
        CartItemDTO item1 = new CartItemDTO(1L, 100L, "Product 1", "image1.jpg", 
                new BigDecimal("10.00"), 2, new BigDecimal("20.00"));
        CartItemDTO item2 = new CartItemDTO(2L, 101L, "Product 2", "image2.jpg", 
                new BigDecimal("15.00"), 1, new BigDecimal("15.00"));
        
        List<CartItemDTO> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        
        CartDTO cartDTO = new CartDTO(1L, 1L, "testuser", items, 
                new BigDecimal("35.00"), 3);

        // When
        String json = objectMapper.writeValueAsString(cartDTO);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("testuser"));
        assertTrue(json.contains("Product 1"));
        assertTrue(json.contains("Product 2"));
        assertFalse(json.contains("\"cart\":")); // No circular reference
    }

    @Test
    void testCartItemDTOSerialization() throws Exception {
        // Given
        CartItemDTO itemDTO = new CartItemDTO(1L, 100L, "Test Product", "test.jpg", 
                new BigDecimal("25.50"), 3, new BigDecimal("76.50"));

        // When
        String json = objectMapper.writeValueAsString(itemDTO);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("Test Product"));
        assertTrue(json.contains("25.5"));
        assertFalse(json.contains("\"cart\":")); // No circular reference
    }

    @Test
    void testOrderDTOSerialization() throws Exception {
        // Given
        OrderDetailDTO detail1 = new OrderDetailDTO(1L, 1L, 100L, "Product 1", 
                "image1.jpg", 2, new BigDecimal("10.00"));
        OrderDetailDTO detail2 = new OrderDetailDTO(2L, 1L, 101L, "Product 2", 
                "image2.jpg", 1, new BigDecimal("15.00"));
        
        List<OrderDetailDTO> details = new ArrayList<>();
        details.add(detail1);
        details.add(detail2);
        
        OrderDTO orderDTO = new OrderDTO(1L, 1L, "testuser", LocalDateTime.now(), 
                new BigDecimal("35.00"), Order.OrderStatus.PENDING, "123 Test St", details);

        // When
        String json = objectMapper.writeValueAsString(orderDTO);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("testuser"));
        assertTrue(json.contains("PENDING"));
        assertTrue(json.contains("Product 1"));
        assertFalse(json.contains("\"order\":")); // No circular reference
    }

    @Test
    void testOrderDetailDTOSerialization() throws Exception {
        // Given
        OrderDetailDTO detailDTO = new OrderDetailDTO(1L, 1L, 100L, "Test Product", 
                "test.jpg", 2, new BigDecimal("25.50"));

        // When
        String json = objectMapper.writeValueAsString(detailDTO);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("Test Product"));
        assertTrue(json.contains("25.5"));
        assertFalse(json.contains("\"order\":")); // No circular reference
    }

    @Test
    void testUserDTOSerialization() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO(1L, "testuser", "Test User", 
                "test@example.com", "1234567890", User.Role.ROLE_USER);

        // When
        String json = objectMapper.writeValueAsString(userDTO);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("testuser"));
        assertTrue(json.contains("test@example.com"));
        assertFalse(json.contains("password")); // No password field
        assertFalse(json.contains("\"cart\":")); // No circular reference
        assertFalse(json.contains("\"orders\":")); // No circular reference
    }
}
