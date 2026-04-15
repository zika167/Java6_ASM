package poly.edu.asm_be.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.asm_be.dto.ProductDTO;
import poly.edu.asm_be.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // Get featured products for homepage (limit to 10)
        Pageable pageable = PageRequest.of(0, 10);
        List<ProductDTO> featuredProducts = productService.getAllActiveProducts(pageable).getContent();
        
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("pageTitle", "Home");
        
        return "index";
    }
}