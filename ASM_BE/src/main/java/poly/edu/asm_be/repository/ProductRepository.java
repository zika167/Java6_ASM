package poly.edu.asm_be.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.edu.asm_be.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrue();
    Page<Product> findByIsActiveTrue(Pageable pageable);
    List<Product> findByCategoryIdAndIsActiveTrue(Long categoryId);
    Page<Product> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND " +
           "(p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    List<Product> searchProducts(@Param("keyword") String keyword);
}