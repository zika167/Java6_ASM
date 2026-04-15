package poly.edu.asm_be.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminPageController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String adminProducts(Model model) {
        model.addAttribute("pageTitle", "Product Management");
        return "admin/products";
    }

    @GetMapping("/categories")
    public String adminCategories(Model model) {
        model.addAttribute("pageTitle", "Category Management");
        return "admin/categories";
    }

    @GetMapping("/orders")
    public String adminOrders(Model model) {
        model.addAttribute("pageTitle", "Order Management");
        return "admin/orders";
    }

    @GetMapping("/users")
    public String adminUsers(Model model) {
        model.addAttribute("pageTitle", "User Management");
        return "admin/users";
    }
}