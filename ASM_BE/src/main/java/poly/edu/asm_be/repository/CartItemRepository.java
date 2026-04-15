package poly.edu.asm_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.edu.asm_be.entity.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId")
    List<CartItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    Optional<CartItem> findCartItemByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
    
    void deleteByCartIdAndProductId(Long cartId, Long productId);
    void deleteByCartId(Long cartId);
}