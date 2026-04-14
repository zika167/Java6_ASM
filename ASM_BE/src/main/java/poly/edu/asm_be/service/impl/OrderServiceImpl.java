package poly.edu.asm_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poly.edu.asm_be.dto.OrderDTO;
import poly.edu.asm_be.dto.OrderDetailDTO;
import poly.edu.asm_be.entity.Order;
import poly.edu.asm_be.entity.OrderDetail;
import poly.edu.asm_be.entity.Product;
import poly.edu.asm_be.entity.User;
import poly.edu.asm_be.repository.OrderDetailRepository;
import poly.edu.asm_be.repository.OrderRepository;
import poly.edu.asm_be.repository.ProductRepository;
import poly.edu.asm_be.repository.UserRepository;
import poly.edu.asm_be.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }
    
    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderDTO> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        
        // Save order details
        if (orderDTO.getOrderDetails() != null) {
            for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(savedOrder);
                
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                detail.setProduct(product);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setPrice(detailDTO.getPrice());
                
                orderDetailRepository.save(detail);
            }
        }
        
        return convertToDTO(savedOrder);
    }
    
    @Override
    public OrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        existingOrder.setStatus(status);
        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDTO(updatedOrder);
    }
    
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUsername(order.getUser().getUsername());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getAddress());
        
        if (order.getOrderDetails() != null) {
            List<OrderDetailDTO> detailDTOs = order.getOrderDetails()
                    .stream()
                    .map(this::convertDetailToDTO)
                    .collect(Collectors.toList());
            dto.setOrderDetails(detailDTOs);
        }
        
        return dto;
    }
    
    private OrderDetailDTO convertDetailToDTO(OrderDetail detail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(detail.getId());
        dto.setOrderId(detail.getOrder().getId());
        dto.setProductId(detail.getProduct().getId());
        dto.setProductName(detail.getProduct().getName());
        dto.setProductImage(detail.getProduct().getImage());
        dto.setQuantity(detail.getQuantity());
        dto.setPrice(detail.getPrice());
        return dto;
    }
    
    private Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDateTime.now());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : Order.OrderStatus.PENDING);
        order.setAddress(dto.getAddress());
        
        return order;
    }
}