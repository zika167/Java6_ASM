# Implementation Plan: Backend Refactoring and Simplification

## Overview

This implementation plan breaks down the refactoring of the Java Spring Boot e-commerce backend into discrete, sequential tasks. The refactoring removes over-engineered components (Swagger/OpenAPI, complex logging, multiple environment configs, service/impl split) while maintaining all core functionality.

## Tasks

- [x] 1. Remove Swagger/OpenAPI dependencies and configuration
  - Remove springdoc-openapi-starter-webmvc-ui dependency from pom.xml
  - Remove swagger-annotations dependency from pom.xml
  - Delete ASM_BE/src/main/java/poly/edu/asm_be/config/OpenApiConfig.java
  - Remove Swagger configuration properties from application.properties
  - _Requirements: 4_

- [x] 2. Remove Swagger annotations from all controller classes
  - [x] 2.1 Remove Swagger annotations from AdminController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.2 Remove Swagger annotations from AuthController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.3 Remove Swagger annotations from CartController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.4 Remove Swagger annotations from CategoryController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.5 Remove Swagger annotations from OrderController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.6 Remove Swagger annotations from ProductController.java (API)
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_
  
  - [x] 2.7 Remove Swagger annotations from UserController.java
    - Remove @Tag, @Operation, @ApiResponse annotations
    - Remove io.swagger.v3.oas.annotations.* imports
    - _Requirements: 4_

- [x] 3. Remove complex logging configuration
  - Delete ASM_BE/src/main/resources/logback-spring.xml
  - Delete ASM_BE/logs/ directory (entire folder)
  - Update application.properties to keep only basic logging levels
  - Remove custom logging configuration beyond logging.level.* properties
  - _Requirements: 5_

- [x] 4. Remove environment-specific configuration files
  - Delete ASM_BE/src/main/resources/application-prod.properties
  - Consolidate all necessary configuration into application.properties
  - Remove Spring profile-specific configurations
  - _Requirements: 6_

- [x] 5. Checkpoint - Verify application starts successfully
  - Ensure all tests pass, ask the user if questions arise.

- [x] 6. Merge service implementations into service classes
  - [x] 6.1 Merge AuthServiceImpl into AuthService
    - Copy implementation code from AuthServiceImpl.java into AuthService.java
    - Convert AuthService from interface to concrete @Service class
    - Update @Autowired references in AuthController if needed
    - _Requirements: 13_
  
  - [x] 6.2 Merge CartServiceImpl into CartService
    - Copy implementation code from CartServiceImpl.java into CartService.java
    - Convert CartService from interface to concrete @Service class
    - Update @Autowired references in CartController if needed
    - _Requirements: 13_
  
  - [x] 6.3 Merge CategoryServiceImpl into CategoryService
    - Copy implementation code from CategoryServiceImpl.java into CategoryService.java
    - Convert CategoryService from interface to concrete @Service class
    - Update @Autowired references in CategoryController if needed
    - _Requirements: 13_
  
  - [x] 6.4 Merge OrderServiceImpl into OrderService
    - Copy implementation code from OrderServiceImpl.java into OrderService.java
    - Convert OrderService from interface to concrete @Service class
    - Update @Autowired references in OrderController if needed
    - _Requirements: 13_
  
  - [x] 6.5 Merge ProductServiceImpl into ProductService
    - Copy implementation code from ProductServiceImpl.java into ProductService.java
    - Convert ProductService from interface to concrete @Service class
    - Update @Autowired references in ProductController (API) if needed
    - _Requirements: 13_
  
  - [x] 6.6 Merge UserServiceImpl into UserService
    - Copy implementation code from UserServiceImpl.java into UserService.java
    - Convert UserService from interface to concrete @Service class
    - Update @Autowired references in UserController if needed
    - _Requirements: 13_

- [x] 7. Delete service/impl directory
  - Delete ASM_BE/src/main/java/poly/edu/asm_be/service/impl/ directory
  - Verify all service implementations have been merged
  - _Requirements: 13_

- [x] 8. Simplify DTOs to prevent infinite recursion only
  - [x] 8.1 Review and simplify CartDTO
    - Keep only fields: id, userId, username, cartItems, totalAmount, totalItems
    - Remove unnecessary fields and complex mapping logic
    - _Requirements: 7_
  
  - [x] 8.2 Review and simplify CartItemDTO
    - Keep only fields: id, productId, productName, productImage, price, quantity, subtotal
    - Remove unnecessary fields and complex mapping logic
    - _Requirements: 7_
  
  - [x] 8.3 Review and simplify OrderDTO
    - Keep only fields: id, userId, username, orderDate, totalAmount, status, address, orderDetails
    - Remove unnecessary fields and complex mapping logic
    - _Requirements: 7_
  
  - [x] 8.4 Review and simplify OrderDetailDTO
    - Keep only fields: id, orderId, productId, productName, productImage, quantity, price
    - Remove unnecessary fields and complex mapping logic
    - _Requirements: 7_
  
  - [x] 8.5 Review and simplify UserDTO
    - Keep only fields: id, username, fullname, email, phone, role
    - Remove password and other sensitive fields
    - _Requirements: 7_

- [x] 9. Simplify GlobalExceptionHandler
  - Keep only basic exception handlers: EntityNotFoundException, IllegalArgumentException, RuntimeException, Exception
  - Remove custom exception hierarchies if any exist
  - Use simple error messages without elaborate error codes
  - Return consistent ApiResponse format for all exceptions
  - _Requirements: 8_

- [x] 10. Update and verify SecurityConfig
  - [x] 10.1 Review SecurityConfig for clear role-based rules
    - Verify public access: /, /home, /products/**, /api/v1/auth/**, /login, /register, static resources
    - Verify ROLE_USER access: /cart/**, /checkout/**, /orders/**, /api/v1/cart/**, /api/v1/orders/**, /api/v1/users/profile/**
    - Verify ROLE_ADMIN access: /admin/**, /api/v1/admin/**
    - _Requirements: 3_
  
  - [x] 10.2 Verify authentication success handler
    - Ensure admin users redirect to /admin
    - Ensure regular users redirect to /
    - _Requirements: 3_

- [x] 11. Simplify application.properties
  - Keep only essential configuration: database connection, JPA settings, server port, Thymeleaf settings, basic logging
  - Remove Swagger/OpenAPI configuration lines
  - Remove complex logging configuration
  - Remove app.checkout.* properties (hardcode if needed)
  - _Requirements: 6_

- [x] 12. Checkpoint - Verify all endpoints work
  - Ensure all tests pass, ask the user if questions arise.

- [ ]* 13. Manual testing of refactored system
  - [ ]* 13.1 Test product browsing (Thymeleaf pages)
    - Verify product listing page renders correctly
    - Verify product detail page renders correctly
    - _Requirements: 1_
  
  - [ ]* 13.2 Test user authentication
    - Verify user registration works
    - Verify user login works
    - Verify role-based redirects work (admin → /admin, user → /)
    - _Requirements: 3_
  
  - [ ]* 13.3 Test cart operations (REST APIs)
    - Verify add to cart works
    - Verify update cart quantity works
    - Verify remove from cart works
    - Verify cart persists in database
    - _Requirements: 2, 9_
  
  - [ ]* 13.4 Test checkout flow
    - Verify checkout creates Order with PENDING status
    - Verify checkout creates OrderDetail records
    - Verify checkout clears cart after successful order
    - _Requirements: 2, 10_
  
  - [ ]* 13.5 Test order viewing
    - Verify users can view their own orders
    - Verify order details display correctly
    - _Requirements: 2_
  
  - [ ]* 13.6 Test admin operations (REST APIs)
    - Verify admin can manage products (CRUD)
    - Verify admin can manage users
    - Verify admin can view all orders
    - Verify non-admin users cannot access admin endpoints
    - _Requirements: 2, 3_
  
  - [ ]* 13.7 Test role-based access control
    - Verify ROLE_USER can access cart, orders, account management
    - Verify ROLE_USER cannot access admin pages
    - Verify ROLE_ADMIN can access all admin pages
    - Verify public access to product browsing works
    - _Requirements: 3_
  
  - [ ]* 13.8 Test JSON serialization
    - Verify no infinite recursion in cart API responses
    - Verify no infinite recursion in order API responses
    - Verify DTOs correctly prevent circular references
    - _Requirements: 7_

- [x] 14. Final checkpoint - Verify refactoring complete
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Tasks marked with `*` are optional manual testing tasks that can be skipped for faster completion
- Each task references specific requirements for traceability
- Checkpoints ensure incremental validation after major refactoring steps
- The refactoring maintains all core functionality while removing unnecessary complexity
- Service layer refactoring (tasks 6-7) is the most critical step requiring careful merging of implementation code
- DTO simplification (task 8) focuses on keeping only fields necessary to prevent infinite recursion
- Manual testing (task 13) validates that all functionality works after refactoring
