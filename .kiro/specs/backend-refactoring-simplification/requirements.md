# Requirements Document

## Introduction

This document specifies the requirements for refactoring and simplifying a Java Spring Boot e-commerce backend project to meet basic assignment requirements. The refactoring will remove over-engineered components while maintaining core functionality for product browsing, shopping cart, order placement, account management, and admin operations with proper role-based security.

## Glossary

- **Backend_System**: The Java Spring Boot e-commerce application being refactored
- **User**: A customer with ROLE_USER who can browse products, manage cart, place orders, and manage their account
- **Admin**: A user with ROLE_ADMIN who can manage products, customers, and orders
- **Thymeleaf_Page**: Server-side rendered HTML page using Spring @Controller and Thymeleaf template engine
- **REST_API**: JSON-based API endpoint using Spring @RestController
- **DTO**: Data Transfer Object used to prevent infinite recursion in JSON serialization
- **Swagger**: OpenAPI documentation framework to be removed
- **Environment_Config**: Profile-specific configuration files (e.g., application-prod.properties) to be removed

## Requirements

### Requirement 1: Maintain Thymeleaf Pages for Product Browsing

**User Story:** As a user, I want to browse products using server-rendered pages, so that I can view product listings and details without JavaScript dependencies.

#### Acceptance Criteria

1. THE Backend_System SHALL use @Controller with Thymeleaf templates for the product listing page
2. THE Backend_System SHALL use @Controller with Thymeleaf templates for the product detail page
3. THE Backend_System SHALL NOT use @RestController or VueJS for product browsing pages
4. WHEN a user accesses the product listing page, THE Backend_System SHALL render the page using Thymeleaf
5. WHEN a user accesses a product detail page, THE Backend_System SHALL render the page using Thymeleaf

### Requirement 2: Maintain REST APIs with VueJS for Interactive Features

**User Story:** As a user, I want to use interactive features like shopping cart and order placement through modern web APIs, so that I can have a responsive user experience.

#### Acceptance Criteria

1. THE Backend_System SHALL use @RestController with VueJS (Axios) for shopping cart operations
2. THE Backend_System SHALL use @RestController with VueJS (Axios) for order placement
3. THE Backend_System SHALL use @RestController with VueJS (Axios) for account management
4. THE Backend_System SHALL use @RestController with VueJS (Axios) for ALL admin pages (product management, customer management, order management)
5. THE Backend_System SHALL NOT use @Controller with Thymeleaf for shopping cart, orders, account management, or admin pages

### Requirement 3: Maintain Role-Based Security

**User Story:** As a system administrator, I want clear role separation between users and admins, so that access to sensitive operations is properly controlled.

#### Acceptance Criteria

1. THE Backend_System SHALL implement ROLE_USER with permissions to place orders, view personal orders, and change password
2. THE Backend_System SHALL implement ROLE_ADMIN with permissions to access all admin pages
3. WHEN a user with ROLE_USER attempts to access admin pages, THE Backend_System SHALL deny access
4. WHEN a user with ROLE_ADMIN accesses admin pages, THE Backend_System SHALL grant access
5. THE Backend_System SHALL require login for cart operations, order placement, account management, and admin pages
6. THE Backend_System SHALL allow public access to product listing and product detail pages

### Requirement 4: Remove Swagger/OpenAPI Documentation

**User Story:** As a developer simplifying the project, I want to remove Swagger/OpenAPI documentation, so that the project meets basic assignment requirements without unnecessary complexity.

#### Acceptance Criteria

1. THE Backend_System SHALL NOT include Swagger/OpenAPI dependencies in pom.xml
2. THE Backend_System SHALL NOT include OpenApiConfig.java configuration file
3. THE Backend_System SHALL NOT include Swagger annotations in controller classes
4. THE Backend_System SHALL NOT expose /swagger-ui/** or /v3/api-docs/** endpoints
5. THE Backend_System SHALL remove all Swagger-related configuration from application.properties

### Requirement 5: Remove Complex Logging Configuration

**User Story:** As a developer simplifying the project, I want to remove complex logging configuration, so that the project uses Spring Boot's default logging without custom configuration files.

#### Acceptance Criteria

1. THE Backend_System SHALL NOT include logback-spring.xml configuration file
2. THE Backend_System SHALL NOT include custom logging configuration in application.properties beyond basic log levels
3. THE Backend_System SHALL use Spring Boot's default logging configuration
4. THE Backend_System SHALL remove the logs directory from the project structure

### Requirement 6: Remove Environment-Specific Configuration Files

**User Story:** As a developer simplifying the project, I want to use a single configuration file, so that the project is easier to understand and maintain for assignment purposes.

#### Acceptance Criteria

1. THE Backend_System SHALL use only ONE application.properties file
2. THE Backend_System SHALL NOT include application-prod.properties or other profile-specific configuration files
3. THE Backend_System SHALL include basic database connection configuration in application.properties
4. THE Backend_System SHALL include basic JPA configuration in application.properties
5. THE Backend_System SHALL remove all Spring profile-specific configurations

### Requirement 7: Simplify DTO Usage

**User Story:** As a developer simplifying the project, I want to minimize excessive DTO usage, so that the codebase is simpler while still preventing JSON serialization issues.

#### Acceptance Criteria

1. WHERE DTOs are necessary to prevent infinite recursion, THE Backend_System SHALL use simple DTOs with only basic fields
2. THE Backend_System SHALL NOT use DTOs for entities that do not cause infinite recursion
3. THE Backend_System SHALL NOT include complex DTO mapping logic beyond basic field copying
4. WHEN returning JSON responses, THE Backend_System SHALL use entities directly where possible
5. WHEN infinite recursion would occur, THE Backend_System SHALL use simple DTOs with basic fields only

### Requirement 8: Simplify Global Exception Handler

**User Story:** As a developer simplifying the project, I want a basic exception handler, so that APIs don't crash but without over-engineered error handling.

#### Acceptance Criteria

1. THE Backend_System SHALL include a GlobalExceptionHandler to catch basic exceptions
2. THE Backend_System SHALL handle only the most common exception types (e.g., RuntimeException, IllegalArgumentException)
3. THE Backend_System SHALL NOT include elaborate exception handling with custom exception hierarchies
4. WHEN an unhandled exception occurs, THE Backend_System SHALL return a basic error response to prevent API crashes
5. THE Backend_System SHALL NOT include detailed exception logging or monitoring beyond basic error messages

### Requirement 9: Maintain Database-Backed Shopping Cart

**User Story:** As a user, I want my shopping cart to be persisted in the database, so that my cart items are saved between sessions.

#### Acceptance Criteria

1. THE Backend_System SHALL maintain Cart entity in the database
2. THE Backend_System SHALL maintain CartItem entity in the database
3. THE Backend_System SHALL minimize complex query logic for cart operations
4. WHEN a user adds an item to cart, THE Backend_System SHALL save the CartItem to the database
5. WHEN a user views their cart, THE Backend_System SHALL retrieve cart items from the database using simple queries

### Requirement 10: Simplify Checkout Flow

**User Story:** As a user, I want a simple checkout process, so that I can place orders without complex payment integration.

#### Acceptance Criteria

1. WHEN a user initiates checkout, THE Backend_System SHALL receive the cart items list
2. WHEN processing checkout, THE Backend_System SHALL create an Order with "Pending" status
3. WHEN processing checkout, THE Backend_System SHALL create OrderDetail records for each cart item
4. WHEN checkout is complete, THE Backend_System SHALL delete the cart items
5. WHEN checkout is complete, THE Backend_System SHALL return a success response
6. THE Backend_System SHALL NOT integrate with external payment gateways
7. THE Backend_System SHALL NOT include complex order validation beyond basic field checks

### Requirement 11: Maintain Core Entities

**User Story:** As a developer, I want to keep all core database entities, so that the application maintains its data model.

#### Acceptance Criteria

1. THE Backend_System SHALL maintain User entity
2. THE Backend_System SHALL maintain Product entity
3. THE Backend_System SHALL maintain Category entity
4. THE Backend_System SHALL maintain Cart entity
5. THE Backend_System SHALL maintain CartItem entity
6. THE Backend_System SHALL maintain Order entity
7. THE Backend_System SHALL maintain OrderDetail entity

### Requirement 12: Maintain Core Dependencies

**User Story:** As a developer, I want to keep only essential Spring Boot dependencies, so that the project has minimal external dependencies.

#### Acceptance Criteria

1. THE Backend_System SHALL include spring-boot-starter-web dependency
2. THE Backend_System SHALL include spring-boot-starter-data-jpa dependency
3. THE Backend_System SHALL include spring-boot-starter-security dependency
4. THE Backend_System SHALL include spring-boot-starter-thymeleaf dependency
5. THE Backend_System SHALL include spring-boot-starter-validation dependency
6. THE Backend_System SHALL include mariadb-java-client dependency
7. THE Backend_System SHALL include lombok dependency
8. THE Backend_System SHALL NOT include springdoc-openapi dependencies
9. THE Backend_System SHALL NOT include swagger-annotations dependency

### Requirement 13: Simplified Project Structure

**User Story:** As a developer reviewing the project, I want a clear and simple folder structure, so that I can easily understand the project organization.

#### Acceptance Criteria

1. THE Backend_System SHALL maintain the following package structure: api, config, controller, dto, entity, exception, repository, service
2. THE Backend_System SHALL NOT include the service/impl subdirectory if service interfaces can be simplified to concrete classes
3. THE Backend_System SHALL remove OpenApiConfig.java from the config package
4. THE Backend_System SHALL remove logback-spring.xml from resources
5. THE Backend_System SHALL remove application-prod.properties from resources
6. THE Backend_System SHALL maintain only one application.properties file in resources

## Proposed Simplified Structure

```
ASM_BE/
├── src/main/java/poly/edu/asm_be/
│   ├── AsmBeApplication.java
│   ├── api/                          # @RestController for JSON APIs
│   │   ├── AdminController.java      # Admin management APIs
│   │   ├── AuthController.java       # Login/Register APIs
│   │   ├── CartController.java       # Cart APIs
│   │   ├── CategoryController.java   # Category APIs (if needed)
│   │   ├── OrderController.java      # Order APIs
│   │   ├── ProductController.java    # Product APIs (if needed)
│   │   └── UserController.java       # User management APIs
│   ├── config/
│   │   ├── SecurityConfig.java       # Simplified security config
│   │   └── WebConfig.java            # Basic web config (if needed)
│   ├── controller/                   # @Controller for Thymeleaf pages
│   │   ├── HomeController.java       # Home page
│   │   └── ProductController.java    # Product listing & detail pages
│   ├── dto/                          # Minimal DTOs only where needed
│   │   ├── ApiResponse.java
│   │   ├── AuthResponse.java
│   │   ├── CartDTO.java              # Only if needed for infinite recursion
│   │   ├── CartItemDTO.java          # Only if needed
│   │   ├── ChangePasswordRequest.java
│   │   ├── LoginRequest.java
│   │   ├── OrderDTO.java             # Only if needed
│   │   ├── ProductDTO.java           # Only if needed
│   │   ├── RegisterRequest.java
│   │   └── UserDTO.java              # Only if needed
│   ├── entity/
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Category.java
│   │   ├── Order.java
│   │   ├── OrderDetail.java
│   │   ├── Product.java
│   │   └── User.java
│   ├── exception/
│   │   └── GlobalExceptionHandler.java  # Simplified exception handler
│   ├── repository/
│   │   ├── CartItemRepository.java
│   │   ├── CartRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── OrderDetailRepository.java
│   │   ├── OrderRepository.java
│   │   ├── ProductRepository.java
│   │   └── UserRepository.java
│   └── service/                      # Service classes (no separate impl/ folder)
│       ├── AuthService.java
│       ├── CartService.java
│       ├── CategoryService.java
│       ├── CustomUserDetailsService.java
│       ├── OrderService.java
│       ├── ProductService.java
│       └── UserService.java
├── src/main/resources/
│   ├── application.properties        # Single config file
│   ├── data.sql                      # Keep if needed
│   ├── schema.sql                    # Keep if needed
│   ├── static/                       # CSS, JS, images
│   └── templates/                    # Thymeleaf templates
└── pom.xml                           # Simplified dependencies

FILES TO REMOVE:
- src/main/java/poly/edu/asm_be/config/OpenApiConfig.java
- src/main/resources/application-prod.properties
- src/main/resources/logback-spring.xml
- logs/ directory (entire folder)
- service/impl/ subdirectory (merge into service/)

DEPENDENCIES TO REMOVE FROM pom.xml:
- springdoc-openapi-starter-webmvc-ui
- swagger-annotations

CONFIGURATION TO REMOVE FROM application.properties:
- Swagger/OpenAPI configuration lines
- Complex logging configuration (keep only basic log levels)
- Application-specific config (app.checkout.*) unless truly needed
```
