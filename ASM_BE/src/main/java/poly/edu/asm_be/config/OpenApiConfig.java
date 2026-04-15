package poly.edu.asm_be.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java 6 E-Commerce API")
                        .description("Comprehensive REST API for Java 6 E-Commerce E-Store application. " +
                                   "This API provides endpoints for user management, product catalog, " +
                                   "shopping cart operations, order processing, and administrative functions. " +
                                   "Built with Spring Boot 3.x, Spring Security, and follows RESTful principles.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Java 6 Development Team")
                                .email("support@java6-ecommerce.com")
                                .url("https://github.com/java6-ecommerce"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.java6-ecommerce.com")
                                .description("Production Server")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .addSecurityItem(new SecurityRequirement().addList("Session Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Bearer Token Authentication"))
                        .addSecuritySchemes("Session Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("JSESSIONID")
                                .description("Session-based Authentication (used by web interface)")));
    }
}