package poly.edu.asm_be.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - không cần đăng nhập
                .requestMatchers("/", "/home", "/products/**", "/categories/**").permitAll()
                .requestMatchers("/api/v1/products/**", "/api/v1/categories/**").permitAll()
                .requestMatchers("/api/v1/auth/**", "/api/v1/config/**").permitAll()
                .requestMatchers("/login", "/register", "/logout").permitAll()
                .requestMatchers("/auth/**").permitAll()
                
                // Static resources - CSS, JS, Images
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                .requestMatchers("/webjars/**", "/favicon.ico").permitAll()
                
                // User endpoints - yêu cầu ROLE_USER hoặc ROLE_ADMIN
                .requestMatchers("/cart/**", "/checkout/**", "/orders/**", "/account/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/cart/**", "/api/v1/orders/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/users/profile/**").hasAnyRole("USER", "ADMIN")
                
                // Admin endpoints - chỉ ROLE_ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                
                // Tất cả request khác yêu cầu authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform-login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/";
            
            // Kiểm tra role để redirect phù hợp
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            
            if (isAdmin) {
                redirectUrl = "/admin";
            }
            
            // Kiểm tra nếu có redirect parameter
            String targetUrl = request.getParameter("redirect");
            if (targetUrl != null && !targetUrl.isEmpty()) {
                redirectUrl = targetUrl;
            }
            
            response.sendRedirect(redirectUrl);
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        };
    }
}