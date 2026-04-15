# Design Document: Backend Refactoring and Simplification

## Overview

This design document specifies the refactoring approach for simplifying a Java Spring Boot e-commerce backend to meet basic assignment requirements. The refactoring removes over-engineered components (Swagger/OpenAPI, complex logging, multiple environment configs, excessive service interfaces) while maintaining all core functionality: product browsing, shopping cart, order placement, account management, and admin operations with role-based security.

### Design Philosophy

The refactored system follows a "student assignment" level of complexity:
- **Simple but complete**: All features work, but without enterprise-level abstractions
- **Clear separation**: @Controller for Thymeleaf pages, @RestController for JSON APIs
- **Minimal DTOs**: Only where needed to prevent infinite recursion in JSON serialization
- **Direct service classes**: No interface/implementation split unless truly needed
- **Single configuration**: One application.properties file
- **Basic exception handling**: Prevent crashes without elaborate error hierarchies

### Technology Stack

- **Spring Boot 3.2.4** with Java 17
- **Spring Data JPA** with MariaDB
- **Spring Security** for authentication and authorization
- **Thymeleaf** for server-side rendering (product pages)
- **VueJS + Axios** for interactive features (cart, orders, admin)
- **Lombok** for boilerplate reduction

## Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Client Layer                          │
├──────────────────────────┬──────────────────────────────────┤
│   Thymeleaf Templates    │    VueJS + Axios Frontend        │
│   (Product Browsing)     │    (Cart, Orders, Admin)         │
└──────────────┬───────────┴──────────────┬───────────────────┘
               │                          │
               ▼                          ▼
┌──────────────────────────┐  ┌──────────────────────────────┐
│   @Controller Layer      │  │   @RestController Layer      │
│   (Thymeleaf Pages)      │  │   (JSON APIs)                │
└──────────────┬───────────┘  └──────────────┬───────────────┘
               │                              │
               └──────────────┬───────────────┘
                              ▼
                   ┌──────────────────────┐
                   │   Security Layer     │
                   │   (SecurityConfig)   │
                   └──────────┬───────────┘
                              ▼
                   ┌──────────────────────┐
                   │   Service Layer      │
                   │   (Business Logic)   │
                   └──────────┬───────────┘
                              ▼
                   ┌──────────────────────┐
                   │   Repository Layer   │
                   │   (Data Access)      │
                   └──────────┬───────────┘
                              ▼
                   ┌──────────────────────┐
                   │   Entity Layer       │
                   │   (JPA Entities)     │
                   └──────────┬───────────┘
                              ▼
                   ┌──────────────────────┐
                   │   MariaDB Database   │
                   └──────────────────────┘
```

### Controller Architecture

The system uses two distinct controller types:

**@Controller (Thymeleaf Pages)**
- **Purpose**: Server-side rendered HTML pages
- **Use cases**: Product listing, product detail pages
- **Returns**: View names (String) that resolve to Thymeleaf templates
- **Example**: `HomeController`, `ProductController`

**@RestController (JSON APIs)**
- **Purpose**: RESTful JSON APIs for interactive features
- **Use cases**: Cart operations, order placement, account management, all admin operations
- **Returns**: JSON responses (entities or DTOs)
- **Example**: `CartController`, `OrderController`, `AdminController`

### Package Structure

```
poly.edu.asm_be/
├── AsmBeApplication.java
├── api/                          # @RestController for JSON APIs
│   ├── AdminController.java      # Admin CRUD operations
│   ├── AuthController.java       # Login/Register/Logout APIs
│   ├── CartController.java       # Cart management APIs
│   ├── CategoryController.java   # Category APIs (if needed)
│   ├── OrderController.java      # Order management APIs
│   ├── ProductController.java    # Product APIs (if needed)
│   └── UserController.java       # User profile APIs
├── config/
│   ├── SecurityConfig.java       # Security configuration
│   └── WebConfig.java            # Web configuration (if needed)
├── controller/                   # @Controller for Thymeleaf pages
│   ├── HomeController.java       # Home page
│   └── ProductController.java    # Product listing & detail
├── dto/                          # Minimal DTOs
│   ├── ApiResponse.java          # Generic API response wrapper
│   ├── AuthResponse.java         # Authentication response
│   ├── CartDTO.java              # Cart with items (prevents recursion)
│   ├── CartItemDTO.java          # Cart item details
│   ├── ChangePasswordRequest.java
│   ├── LoginRequest.java
│   ├── OrderDTO.java             # Order with details (prevents recursion)
│   ├── OrderDetailDTO.java       # Order item details
│   ├── RegisterRequest.java
│   └── UserDTO.java              # User without sensitive data
├── entity/
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Category.java
│   ├── Order.java
│   ├── OrderDetail.java
│   ├── Product.java
│   └── User.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── CartItemRepository.java
│   ├── CartRepository.java
│   ├── CategoryRepository.java
│   ├── OrderDetailRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
└── service/                      # Service classes (no impl/ folder)
    ├── AuthService.java
    ├── CartService.java
    ├── CategoryService.java
    ├── CustomUserDetailsService.java
    ├── OrderService.java
    ├── ProductService.java
    └── UserService.java
```

## Components and Interfaces

### Security Configuration

**SecurityConfig.java** - Simplified security with clear role separation

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public - no authentication required
                .requestMatchers("/", "/home", "/products/**").permitAll()
                .requestMatchers("/api/v1/products/**", "/api/v1/categories/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // User - requires ROLE_USER or ROLE_ADMIN
                .requestMatchers("/cart/**", "/checkout/**", "/orders/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/cart/**", "/api/v1/orders/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/users/profile/**").hasAnyRole("USER", "ADMIN")
                
                // Admin - requires ROLE_ADMIN only
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            response.sendRedirect(isAdmin ? "/admin" : "/");
        };
    }
}
```

**Key Security Rules:**
- Public access: Product browsing, login, register, static resources
- ROLE_USER: Cart, checkout, orders, account management
- ROLE_ADMIN: All admin pages and APIs
- Form-based login with role-based redirect

### Service Layer Design

**Simplified Service Pattern** - Direct concrete classes without interfaces

Current (over-engineered):
```
service/
├── CartService.java (interface)
└── impl/
    └── CartServiceImpl.java (implementation)
```

Refactored (simplified):
```
service/
└── CartService.java (concrete class)
```

**When to keep interfaces:**
- CustomUserDetailsService (required by Spring Security)
- Services with multiple implementations (none in this project)

**Service responsibilities:**
- Business logic and validation
- Transaction management (@Transactional)
- Entity ↔ DTO conversion
- Repository coordination

### DTO Strategy

**Use DTOs only when necessary** to prevent infinite recursion in JSON serialization.

**When DTOs are needed:**

1. **CartDTO** - Prevents User → Cart → CartItem → Product → Category recursion
   ```java
   @Data
   public class CartDTO {
       private Long id;
       private Long userId;
       private String username;
       private List<CartItemDTO> cartItems;
       private BigDecimal totalAmount;
       private Integer totalItems;
   }
   ```

2. **CartItemDTO** - Flattens Product relationship
   ```java
   @Data
   public class CartItemDTO {
       private Long id;
       private Long productId;
       private String productName;
       private String productImage;
       private BigDecimal price;
       private Integer quantity;
       private BigDecimal subtotal;
   }
   ```

3. **OrderDTO** - Prevents User → Order → OrderDetail → Product recursion
   ```java
   @Data
   public class OrderDTO {
       private Long id;
       private Long userId;
       private String username;
       private LocalDateTime orderDate;
       private BigDecimal totalAmount;
       private Order.OrderStatus status;
       private String address;
       private List<OrderDetailDTO> orderDetails;
   }
   ```

4. **OrderDetailDTO** - Flattens Product relationship
   ```java
   @Data
   public class OrderDetailDTO {
       private Long id;
       private Long orderId;
       private Long productId;
       private String productName;
       private String productImage;
       private Integer quantity;
       private BigDecimal price;
   }
   ```

**When DTOs are NOT needed:**
- Simple entities without circular references (Category, Product for listing)
- Request DTOs (LoginRequest, RegisterRequest, ChangePasswordRequest)
- Response wrappers (ApiResponse, AuthResponse)

## Data Models

### Entity Relationships

```
User (1) ──────────── (1) Cart
  │                        │
  │                        │
  │ (1)                    │ (*)
  │                        │
  ▼                        ▼
Order (*)              CartItem (*)
  │                        │
  │                        │
  │ (*)                    │ (*)
  │                        │
  ▼                        ▼
OrderDetail (*)        Product (*)
  │                        │
  │                        │
  │ (*)                    │ (*)
  │                        │
  └────────────────────────┘
                           │
                           │ (*)
                           ▼
                       Category (1)
```

### Entity Descriptions

**User**
- Primary entity for authentication and authorization
- Fields: id, username, password, fullname, email, phone, role, createdAt, updatedAt
- Relationships: One Cart, Many Orders
- Role enum: ROLE_USER, ROLE_ADMIN

**Cart**
- Persistent shopping cart per user
- Fields: id, user, createdAt, updatedAt
- Relationships: One User, Many CartItems
- Lifecycle: Created on first cart operation, cleared after checkout

**CartItem**
- Individual product in cart with quantity
- Fields: id, cart, product, quantity, createdAt, updatedAt
- Relationships: One Cart, One Product
- Unique constraint: (cart_id, product_id)

**Product**
- Product catalog entry
- Fields: id, name, price, description, image, category, isActive, createdAt, updatedAt
- Relationships: One Category, Many CartItems, Many OrderDetails
- Soft delete via isActive flag

**Category**
- Product categorization
- Fields: id, name, isActive, createdAt, updatedAt
- Relationships: Many Products
- Soft delete via isActive flag

**Order**
- Customer order record
- Fields: id, user, orderDate, totalAmount, status, address, updatedAt
- Relationships: One User, Many OrderDetails
- Status enum: PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED

**OrderDetail**
- Individual product in order with quantity and price snapshot
- Fields: id, order, product, quantity, price, createdAt
- Relationships: One Order, One Product
- Price snapshot: Captures product price at order time

### Key Design Decisions

1. **Lazy Loading**: All relationships use FetchType.LAZY to avoid N+1 queries
2. **Cascade Operations**: Appropriate cascades (e.g., Cart → CartItems with orphanRemoval)
3. **Soft Deletes**: Products and Categories use isActive flag instead of hard deletes
4. **Price Snapshot**: OrderDetail stores price at order time (not reference to Product.price)
5. **Timestamps**: CreationTimestamp and UpdateTimestamp for audit trail

## Checkout Flow

### Detailed Checkout Process

The checkout flow is intentionally simple without payment gateway integration.

```
┌─────────────────────────────────────────────────────────────┐
│                    Checkout Flow                             │
└─────────────────────────────────────────────────────────────┘

1. User initiates checkout
   ↓
2. Frontend sends POST /api/v1/orders/checkout
   Request body: { userId, address, cartItems[] }
   ↓
3. OrderService.createOrder()
   ├─ Validate user exists
   ├─ Validate cart has items
   ├─ Calculate total amount
   ├─ Create Order entity (status = PENDING)
   ├─ Save Order to database
   ├─ For each cart item:
   │  ├─ Create OrderDetail entity
   │  ├─ Set product, quantity, price (snapshot)
   │  └─ Save OrderDetail to database
   ├─ Clear cart: CartService.clearCart(userId)
   └─ Return OrderDTO
   ↓
4. Frontend receives success response
   ↓
5. Redirect to order confirmation page
```

### Checkout Implementation

**OrderService.createOrder()**

```java
@Transactional
public OrderDTO createOrder(Long userId, String address, List<CartItemDTO> cartItems) {
    // 1. Validate user
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    
    // 2. Validate cart not empty
    if (cartItems == null || cartItems.isEmpty()) {
        throw new RuntimeException("Cart is empty");
    }
    
    // 3. Calculate total amount
    BigDecimal totalAmount = cartItems.stream()
        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    // 4. Create Order
    Order order = new Order();
    order.setUser(user);
    order.setTotalAmount(totalAmount);
    order.setStatus(Order.OrderStatus.PENDING);
    order.setAddress(address);
    Order savedOrder = orderRepository.save(order);
    
    // 5. Create OrderDetails
    for (CartItemDTO cartItem : cartItems) {
        Product product = productRepository.findById(cartItem.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        OrderDetail detail = new OrderDetail();
        detail.setOrder(savedOrder);
        detail.setProduct(product);
        detail.setQuantity(cartItem.getQuantity());
        detail.setPrice(cartItem.getPrice()); // Price snapshot
        orderDetailRepository.save(detail);
    }
    
    // 6. Clear cart
    cartService.clearCart(userId);
    
    // 7. Return DTO
    return convertToOrderDTO(savedOrder);
}
```

**Key Checkout Rules:**
- Order status starts as PENDING
- OrderDetail captures price at order time (not live Product.price)
- Cart is cleared only after successful order creation
- Transaction ensures atomicity (all or nothing)
- No payment gateway integration (assignment simplification)

## Error Handling

### GlobalExceptionHandler

Simplified exception handler to prevent API crashes without elaborate error hierarchies.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse(false, ex.getMessage()));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse(false, ex.getMessage()));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(false, "An error occurred: " + ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(false, "An unexpected error occurred"));
    }
}
```

**Exception Handling Strategy:**
- Catch common exceptions (EntityNotFoundException, IllegalArgumentException, RuntimeException)
- Return consistent ApiResponse format
- Log errors for debugging (basic logging only)
- No custom exception hierarchies
- No elaborate error codes or internationalization

## Testing Strategy

### Testing Approach

This refactoring project focuses on **manual testing** and **basic integration testing** rather than comprehensive automated testing. Property-based testing is not applicable for this type of refactoring work.

**Why PBT is not applicable:**
- This is a refactoring project, not new feature development
- Focus is on simplification and code organization, not algorithmic correctness
- No complex business logic requiring universal property validation
- Student assignment context prioritizes working code over test coverage

### Recommended Testing

**Manual Testing:**
1. Product browsing (Thymeleaf pages)
2. User registration and login
3. Cart operations (add, update, remove)
4. Checkout flow
5. Order viewing
6. Admin operations (product/user/order management)
7. Role-based access control

**Integration Testing (Optional):**
- Repository layer tests with @DataJpaTest
- Controller layer tests with @WebMvcTest
- Security configuration tests with @SpringBootTest

**Testing Focus:**
- Verify all endpoints work after refactoring
- Confirm role-based security rules
- Validate checkout flow creates orders correctly
- Ensure DTOs prevent infinite recursion

## Configuration

### Single application.properties

Simplified configuration with all settings in one file.

```properties
# Application
spring.application.name=ASM_BE

# Database
spring.datasource.url=jdbc:mariadb://localhost:3306/java6_estore?createDatabaseIfNotExist=true
spring.datasource.username=estore_user
spring.datasource.password=estore_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

# Server
server.port=8080
server.servlet.session.timeout=30m

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8

# Logging (basic only)
logging.level.poly.edu.asm_be=DEBUG
logging.level.org.springframework.security=INFO
```

**Configuration Simplifications:**
- Remove application-prod.properties (single environment)
- Remove Swagger/OpenAPI configuration
- Remove logback-spring.xml (use Spring Boot defaults)
- Remove custom logging configuration
- Remove app.checkout.* properties (hardcode if needed)

## Files to Remove

### Configuration Files
- `src/main/resources/application-prod.properties` - Multiple environment configs not needed
- `src/main/resources/logback-spring.xml` - Use Spring Boot default logging
- `src/main/java/poly/edu/asm_be/config/OpenApiConfig.java` - Swagger removal

### Service Implementation Folder
- `src/main/java/poly/edu/asm_be/service/impl/` - Merge implementations into service classes
  - `AuthServiceImpl.java` → Merge into `AuthService.java`
  - `CartServiceImpl.java` → Merge into `CartService.java`
  - `CategoryServiceImpl.java` → Merge into `CategoryService.java`
  - `OrderServiceImpl.java` → Merge into `OrderService.java`
  - `ProductServiceImpl.java` → Merge into `ProductService.java`
  - `UserServiceImpl.java` → Merge into `UserService.java`

### Logs Directory
- `logs/` - Remove entire directory (use console logging)

### Swagger Annotations
- Remove `@Tag`, `@Operation`, `@ApiResponse` annotations from all controllers
- Remove `io.swagger.v3.oas.annotations.*` imports

## Dependencies to Update

### pom.xml Changes

**Remove these dependencies:**
```xml
<!-- Remove Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>

<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-annotations</artifactId>
    <version>2.2.21</version>
</dependency>
```

**Keep these dependencies:**
```xml
<!-- Core Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Thymeleaf Security -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Dev Tools -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>

<!-- Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

## Implementation Notes

### Refactoring Steps

1. **Remove Swagger** (Requirement 4)
   - Delete OpenApiConfig.java
   - Remove Swagger dependencies from pom.xml
   - Remove Swagger annotations from controllers
   - Remove Swagger config from application.properties

2. **Simplify Logging** (Requirement 5)
   - Delete logback-spring.xml
   - Remove logs/ directory
   - Keep only basic logging in application.properties

3. **Single Configuration** (Requirement 6)
   - Delete application-prod.properties
   - Consolidate all config into application.properties

4. **Merge Service Implementations** (Requirement 13)
   - Copy implementation code into service interface files
   - Convert interfaces to concrete classes
   - Delete service/impl/ folder
   - Update @Autowired references if needed

5. **Simplify DTOs** (Requirement 7)
   - Keep only DTOs that prevent infinite recursion
   - Remove unnecessary DTO fields
   - Simplify DTO mapping logic

6. **Simplify Exception Handler** (Requirement 8)
   - Keep only basic exception types
   - Remove custom exception classes if any
   - Use simple error messages

7. **Update Security Config** (Requirement 3)
   - Verify role-based rules are clear
   - Ensure ROLE_USER and ROLE_ADMIN separation
   - Test authentication flows

### Migration Checklist

- [ ] Remove Swagger dependencies and configuration
- [ ] Delete logback-spring.xml and logs/ directory
- [ ] Delete application-prod.properties
- [ ] Merge service implementations into service classes
- [ ] Delete service/impl/ folder
- [ ] Remove unnecessary DTOs
- [ ] Simplify GlobalExceptionHandler
- [ ] Update application.properties
- [ ] Test all endpoints manually
- [ ] Verify role-based security
- [ ] Test checkout flow
- [ ] Verify no infinite recursion in JSON responses

## Summary

This design provides a clear roadmap for refactoring the e-commerce backend from an over-engineered system to a simple, student-assignment-appropriate application. The refactoring maintains all core functionality while removing unnecessary complexity:

**Removed:**
- Swagger/OpenAPI documentation
- Complex logging configuration
- Multiple environment configurations
- Service interface/implementation split
- Excessive DTOs
- Elaborate exception handling

**Maintained:**
- All 7 entities and their relationships
- Role-based security (ROLE_USER, ROLE_ADMIN)
- Product browsing with Thymeleaf
- Interactive features with VueJS + REST APIs
- Database-backed shopping cart
- Simple checkout flow
- Basic exception handling

The result is a clean, understandable codebase suitable for a student assignment while still demonstrating proper Spring Boot architecture and best practices.
