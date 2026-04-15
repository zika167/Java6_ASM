package poly.edu.asm_be.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityConfigTest {

    private AuthenticationSuccessHandler successHandler;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        SecurityConfig securityConfig = new SecurityConfig();
        successHandler = securityConfig.authenticationSuccessHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testAuthenticationSuccessHandler_AdminUser_RedirectsToAdmin() throws Exception {
        // Create authentication with ROLE_ADMIN
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "admin",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        // Execute the success handler
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirect to /admin
        assertEquals("/admin", response.getRedirectedUrl());
    }

    @Test
    void testAuthenticationSuccessHandler_RegularUser_RedirectsToHome() throws Exception {
        // Create authentication with ROLE_USER
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Execute the success handler
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirect to /
        assertEquals("/", response.getRedirectedUrl());
    }

    @Test
    void testAuthenticationSuccessHandler_WithRedirectParameter_UsesRedirectParameter() throws Exception {
        // Create authentication with ROLE_USER
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Add redirect parameter
        request.setParameter("redirect", "/cart");

        // Execute the success handler
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirect to /cart (redirect parameter takes precedence)
        assertEquals("/cart", response.getRedirectedUrl());
    }

    @Test
    void testAuthenticationSuccessHandler_AdminWithRedirectParameter_UsesRedirectParameter() throws Exception {
        // Create authentication with ROLE_ADMIN
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "admin",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        // Add redirect parameter
        request.setParameter("redirect", "/products");

        // Execute the success handler
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirect to /products (redirect parameter takes precedence even for admin)
        assertEquals("/products", response.getRedirectedUrl());
    }
}
