# Unit Testing Guide - Java 6 E-Commerce

## 🧪 Testing Framework Setup

### Dependencies Included
The project already includes comprehensive testing dependencies in `pom.xml`:

```xml
<!-- Spring Boot Test Starter (includes JUnit 5, Mockito, AssertJ, Hamcrest) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Testing Technologies
- **JUnit 5**: Modern testing framework with annotations and assertions
- **Mockito**: Mocking framework for unit tests
- **AssertJ**: Fluent assertion library
- **Spring Boot Test**: Integration testing support
- **Spring Security Test**: Security testing utilities

## 📁 Test Structure

```
src/test/java/
├── poly/edu/asm_be/
│   ├── config/
│   │   └── TestConfig.java                    # Test configuration
│   ├── service/impl/
│   │   ├── CartServiceImplTest.java          # Cart service unit tests
│   │   └── ProductServiceImplTest.java       # Product service unit tests
│   └── AsmBeApplicationTests.java            # Application context test
```

## 🎯 CartService Unit Tests

### Test Coverage
The `CartServiceImplTest` class provides comprehensive coverage for the most critical e-commerce service:

#### ✅ Success Test Cases
1. **Add Product to Cart** - Successfully adds new product to cart
2. **Update Existing Item** - Increases quantity when product already exists
3. **Update Cart Item** - Modifies quantity of existing cart item
4. **Get Cart by User ID** - Retrieves user's cart with all items
5. **Create New Cart** - Creates cart when user has none
6. **Get Total Items** - Calculates total quantity across all items
7. **Clear Cart** - Removes all items from cart

#### ❌ Exception Test Cases
1. **Product Not Found** - Throws `EntityNotFoundException` for invalid product ID
2. **Product Not Active** - Throws `RuntimeException` for inactive products
3. **Cart Item Not Found** - Throws `EntityNotFoundException` for invalid cart item
4. **User Not Found** - Throws `EntityNotFoundException` for invalid user ID

### Key Testing Patterns

#### 1. Mock Setup with @ExtendWith(MockitoExtension.class)
```java
@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock private CartRepository cartRepository;
    @Mock private CartItemRepository cartItemRepository;
    @Mock private ProductRepository productRepository;
    @Mock private UserRepository userRepository;
    
    @InjectMocks private CartServiceImpl cartService;
}
```

#### 2. Test Data Setup with @BeforeEach
```java
@BeforeEach
void setUp() {
    // Setup test entities (User, Product, Cart, CartItem)
    testUser = new User();
    testUser.setId(1L);
    testUser.setUsername("testuser");
    // ... more setup
}
```

#### 3. Behavior Verification
```java
@Test
void addToCart_Success_WhenProductExistsAndActive() {
    // Given - Setup mocks and test data
    when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
    when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
    
    // When - Execute the method under test
    CartDTO result = cartService.addToCart(userId, productId, quantity);
    
    // Then - Verify results and interactions
    assertNotNull(result);
    assertEquals(testCart.getId(), result.getId());
    verify(cartItemRepository).save(any(CartItem.class));
}
```

#### 4. Exception Testing
```java
@Test
void addToCart_ThrowsException_WhenProductNotFound() {
    // Given
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    
    // When & Then
    EntityNotFoundException exception = assertThrows(
        EntityNotFoundException.class,
        () -> cartService.addToCart(userId, productId, quantity)
    );
    
    assertEquals("Product not found with id: " + productId, exception.getMessage());
}
```

## 🛍️ ProductService Unit Tests

### Test Coverage
The `ProductServiceImplTest` class covers essential product management operations:

#### ✅ Success Test Cases
1. **Get Product by ID** - Retrieves product successfully
2. **Get All Active Products** - Returns only active products
3. **Create Product** - Creates new product with valid category
4. **Search Products** - Finds products by keyword
5. **Get Products by Price Range** - Filters products by price

#### ❌ Exception Test Cases
1. **Product Not Found** - Throws exception for invalid product ID
2. **Category Not Found** - Throws exception when creating product with invalid category

## 🚀 Running Tests

### Run All Unit Tests
```bash
# Run all service unit tests
./mvnw test -Dtest=*ServiceImplTest

# Run specific test class
./mvnw test -Dtest=CartServiceImplTest

# Run specific test method
./mvnw test -Dtest=CartServiceImplTest#addToCart_Success_WhenProductExistsAndActive
```

### Test Output
```
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
```

## 📊 Test Results Summary

### CartServiceImplTest Results
- **Total Tests**: 12
- **Success Cases**: 8
- **Exception Cases**: 4
- **Coverage**: Core cart operations (add, update, remove, clear)

### ProductServiceImplTest Results
- **Total Tests**: 7
- **Success Cases**: 5
- **Exception Cases**: 2
- **Coverage**: Product CRUD and search operations

## 🔧 Testing Best Practices Implemented

### 1. Descriptive Test Names
```java
@Test
@DisplayName("Should successfully add product to cart when product exists and is active")
void addToCart_Success_WhenProductExistsAndActive() { ... }

@Test
@DisplayName("Should throw EntityNotFoundException when product does not exist")
void addToCart_ThrowsException_WhenProductNotFound() { ... }
```

### 2. AAA Pattern (Arrange, Act, Assert)
```java
@Test
void testMethod() {
    // Given (Arrange) - Setup test data and mocks
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    
    // When (Act) - Execute the method under test
    Result result = service.methodUnderTest(id);
    
    // Then (Assert) - Verify results and interactions
    assertNotNull(result);
    verify(repository).findById(id);
}
```

### 3. Mock Verification
```java
// Verify method was called with specific parameters
verify(cartItemRepository).save(any(CartItem.class));

// Verify method was never called
verify(cartItemRepository, never()).delete(any(CartItem.class));

// Verify method was called with exact argument
verify(productRepository).findById(productId);
```

### 4. Exception Testing
```java
// Test exception type and message
EntityNotFoundException exception = assertThrows(
    EntityNotFoundException.class,
    () -> service.methodThatThrows(invalidId)
);
assertEquals("Expected error message", exception.getMessage());
```

### 5. Test Data Isolation
- Each test method has isolated test data
- Mocks are reset between tests automatically
- No shared state between tests

## 🎯 Benefits of Current Test Implementation

### 1. **Fast Execution**
- Unit tests run in milliseconds
- No database or network dependencies
- Isolated from external systems

### 2. **Reliable**
- Deterministic results
- No flaky tests due to external dependencies
- Consistent behavior across environments

### 3. **Maintainable**
- Clear test structure and naming
- Easy to understand test intentions
- Simple to modify when business logic changes

### 4. **Comprehensive Coverage**
- Tests both success and failure scenarios
- Covers edge cases and error conditions
- Validates business logic thoroughly

## 📈 Next Steps for Testing

### Recommended Additions
1. **Integration Tests**: Test complete request/response cycles
2. **Repository Tests**: Test custom query methods with `@DataJpaTest`
3. **Security Tests**: Test authentication and authorization
4. **Performance Tests**: Load testing for critical operations
5. **Contract Tests**: API contract validation

### Test Coverage Goals
- **Unit Tests**: 80%+ coverage for service layer
- **Integration Tests**: Critical user journeys
- **End-to-End Tests**: Key business workflows

---

## 🎉 Conclusion

The Java 6 E-Commerce project now includes professional-grade unit tests that:

- **Validate Core Business Logic**: Cart and product operations thoroughly tested
- **Follow Industry Standards**: JUnit 5, Mockito, and Spring Boot Test best practices
- **Provide Fast Feedback**: Quick execution for development workflow
- **Ensure Code Quality**: Comprehensive coverage of success and error scenarios
- **Support Refactoring**: Reliable safety net for code changes

The testing foundation is now in place to support confident development and deployment of the e-commerce application.

**Status: UNIT TESTING IMPLEMENTED** ✅