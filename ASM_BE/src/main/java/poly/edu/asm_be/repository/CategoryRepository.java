package poly.edu.asm_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.edu.asm_be.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsActiveTrue();
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
    List<Category> searchByName(@Param("keyword") String keyword);
}