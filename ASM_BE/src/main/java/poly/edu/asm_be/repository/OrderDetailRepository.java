package poly.edu.asm_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.edu.asm_be.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
    List<OrderDetail> findByProductId(Long productId);
    
    @Query("SELECT od FROM OrderDetail od WHERE od.order.user.id = :userId")
    List<OrderDetail> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId")
    List<OrderDetail> findOrderDetailsByOrderId(@Param("orderId") Long orderId);
}