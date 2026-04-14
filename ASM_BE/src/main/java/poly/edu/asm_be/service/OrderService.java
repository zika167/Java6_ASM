package poly.edu.asm_be.service;

import poly.edu.asm_be.dto.OrderDTO;
import poly.edu.asm_be.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getOrdersByUserId(Long userId);
    List<OrderDTO> getOrdersByStatus(Order.OrderStatus status);
    List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrderStatus(Long id, Order.OrderStatus status);
    void deleteOrder(Long id);
}