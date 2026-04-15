package poly.edu.asm_be.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.asm_be.dto.ApiResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Value("${app.checkout.shipping-fee}")
    private BigDecimal shippingFee;

    @Value("${app.checkout.tax-rate}")
    private BigDecimal taxRate;

    @Value("${app.checkout.free-shipping-threshold}")
    private BigDecimal freeShippingThreshold;

    @GetMapping("/checkout")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCheckoutConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("shippingFee", shippingFee);
        config.put("taxRate", taxRate);
        config.put("freeShippingThreshold", freeShippingThreshold);
        
        return ResponseEntity.ok(ApiResponse.success("Checkout configuration retrieved successfully", config));
    }
}