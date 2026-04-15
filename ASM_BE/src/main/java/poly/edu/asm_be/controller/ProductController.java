package poly.edu.asm_be.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.asm_be.dto.CategoryDTO;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.service.CategoryService;
import poly.edu.asm_be.service.ProductService;

import java.util.List;

@Controller("productPageController")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String productList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String category,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage;
        
        if (category != null && !category.isEmpty()) {
            // Find category by name and get products
            List<CategoryDTO> categories = categoryService.getAllActiveCategories();
            CategoryDTO selectedCategory = categories.stream()
                    .filter(cat -> cat.getName().toLowerCase().equals(category.toLowerCase()))
                    .findFirst()
                    .orElse(null);
            
            if (selectedCategory != null) {
                List<ProductDTO> categoryProducts = productService.getProductsByCategory(selectedCategory.getId());
                // Convert to page manually for simplicity
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), categoryProducts.size());
                List<ProductDTO> pageContent = categoryProducts.subList(start, end);
                
                model.addAttribute("products", pageContent);
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", (int) Math.ceil((double) categoryProducts.size() / size));
                model.addAttribute("selectedCategory", category);
            } else {
                productPage = productService.getAllActiveProducts(pageable);
                model.addAttribute("products", productPage.getContent());
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", productPage.getTotalPages());
            }
        } else {
            productPage = productService.getAllActiveProducts(pageable);
            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
        }
        
        // Get all categories for filter
        List<CategoryDTO> allCategories = categoryService.getAllActiveCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("pageTitle", "Shop");
        
        return "products/list";
    }

    @GetMapping("/category/{categoryName}")
    public String productsByCategory(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        
        return productList(page, size, categoryName, model);
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        try {
            ProductDTO product = productService.getProductById(id);
            model.addAttribute("product", product);
            
            // Get related products from same category (limit to 4)
            if (product.getCategoryId() != null) {
                List<ProductDTO> relatedProducts = productService.getProductsByCategory(product.getCategoryId())
                        .stream()
                        .filter(p -> !p.getId().equals(id)) // Exclude current product
                        .limit(4)
                        .toList();
                model.addAttribute("relatedProducts", relatedProducts);
            }
            
            model.addAttribute("pageTitle", product.getName());
            
            return "products/detail";
        } catch (RuntimeException e) {
            return "redirect:/products";
        }
    }
}