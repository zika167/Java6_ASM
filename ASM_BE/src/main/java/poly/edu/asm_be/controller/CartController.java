package poly.edu.asm_be.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("cartPageController")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("pageTitle", "Shopping Cart");
        return "cart/index";
    }
}