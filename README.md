# 🛒 Java 6 E-Commerce E-Store

## 📋 Giới thiệu dự án

**Java 6 E-Commerce E-Store** là một ứng dụng thương mại điện tử hiện đại được phát triển với kiến trúc Hybrid độc đáo, kết hợp tối ưu SEO và trải nghiệm người dùng động. Dự án được xây dựng nhằm cung cấp một nền tảng bán hàng trực tuyến hoàn chỉnh với đầy đủ các tính năng từ quản lý sản phẩm, giỏ hàng, đặt hàng đến quản trị hệ thống.

### 🎯 Mục tiêu dự án
- Xây dựng hệ thống e-commerce với hiệu suất cao và khả năng mở rộng
- Tối ưu hóa SEO cho các trang sản phẩm và danh mục
- Cung cấp trải nghiệm người dùng mượt mà với các tính năng tương tác động
- Đảm bảo bảo mật và quản lý phân quyền chặt chẽ
- Áp dụng các best practices trong phát triển phần mềm

## 🚀 Công nghệ sử dụng

### Backend
- **Java 21** - Ngôn ngữ lập trình chính với các tính năng hiện đại
- **Spring Boot 3.2.1** - Framework chính cho backend development
- **Spring Security** - Bảo mật và phân quyền người dùng
- **Spring Data JPA** - Quản lý cơ sở dữ liệu và ORM
- **Hibernate** - Implementation của JPA
- **MariaDB** - Hệ quản trị cơ sở dữ liệu quan hệ
- **Maven** - Quản lý dependencies và build tool

### Frontend
- **Thymeleaf** - Template engine cho server-side rendering (SEO)
- **Vue.js 3** - Framework JavaScript cho các tính năng động
- **Axios** - HTTP client cho API calls
- **Tailwind CSS** - Utility-first CSS framework
- **Material Symbols** - Icon library

### DevOps & Tools
- **Docker & Docker Compose** - Containerization và orchestration
- **Swagger/OpenAPI** - API documentation và testing
- **Logback** - Advanced logging với file rotation
- **JUnit 5 & Mockito** - Unit testing framework
- **Spring Boot DevTools** - Development productivity tools

## 🏗️ Kiến trúc Hybrid độc đáo

### 🌟 Điểm nổi bật của kiến trúc

#### 1. **SEO-Friendly Pages (Server-Side Rendering)**
- **Trang chủ**: Render bằng Thymeleaf để tối ưu SEO
- **Danh sách sản phẩm**: Server-side rendering với pagination
- **Chi tiết sản phẩm**: Thymeleaf template với meta tags tối ưu
- **Lợi ích**: Tốc độ tải nhanh, SEO tốt, crawler-friendly

#### 2. **Dynamic SPA Features (Client-Side)**
- **Giỏ hàng**: Vue.js với real-time updates
- **Thanh toán**: Interactive checkout process
- **Admin Dashboard**: Full SPA experience với CRUD operations
- **Lợi ích**: UX mượt mà, không reload trang, tương tác nhanh

#### 3. **API-First Architecture**
- RESTful APIs với chuẩn JSON response format
- Swagger documentation đầy đủ
- Stateless authentication với session management
- Microservices-ready architecture

## 📁 Cấu trúc dự án

```
Java6_ASM/
├── ASM_BE/                          # Backend Spring Boot Application
│   ├── src/main/java/
│   │   └── poly/edu/asm_be/
│   │       ├── api/                 # REST Controllers
│   │       ├── config/              # Configuration classes
│   │       ├── controller/          # MVC Controllers (Thymeleaf)
│   │       ├── dto/                 # Data Transfer Objects
│   │       ├── entity/              # JPA Entities
│   │       ├── exception/           # Exception handling
│   │       ├── repository/          # Data repositories
│   │       └── service/             # Business logic
│   ├── src/main/resources/
│   │   ├── static/                  # CSS, JS, Images
│   │   ├── templates/               # Thymeleaf templates
│   │   ├── application.properties   # Development config
│   │   ├── application-prod.properties # Production config
│   │   ├── logback-spring.xml       # Logging configuration
│   │   └── data.sql                 # Sample data
│   ├── src/test/                    # Unit tests
│   ├── logs/                        # Application logs
│   ├── docker-compose.yml           # MariaDB container
│   ├── pom.xml                      # Maven dependencies
│   └── run-tests.sh                 # Test runner script
└── README.md                        # Tài liệu dự án
```

## 🛠️ Hướng dẫn cài đặt và chạy dự án

### Yêu cầu hệ thống
- **Java 21** hoặc cao hơn
- **Maven 3.6+**
- **Docker & Docker Compose**
- **Git**

### Bước 1: Clone dự án
```bash
git clone <repository-url>
cd Java6_ASM
```

### Bước 2: Khởi động MariaDB Database
```bash
cd ASM_BE

# Khởi động MariaDB container
docker-compose up -d

# Kiểm tra container đang chạy
docker ps
```

**Thông tin database:**
- Host: `localhost:3306`
- Database: `java6_estore`
- Username: `estore_user`
- Password: `estore_password`

### Bước 3: Build và chạy ứng dụng

#### Chế độ Development (khuyến nghị cho phát triển)
```bash
# Build và chạy với Maven
./mvnw spring-boot:run

# Hoặc với profile development
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Chế độ Production
```bash
# Build JAR file
./mvnw clean package

# Chạy với production profile
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Bước 4: Kiểm tra ứng dụng
Sau khi khởi động thành công, truy cập:
- **Trang chủ**: http://localhost:8080
- **Swagger API**: http://localhost:8080/swagger-ui.html
- **Admin Dashboard**: http://localhost:8080/admin

## 👥 Tài khoản đăng nhập mẫu

### 🔑 Tài khoản Administrator
- **Username**: `admin`
- **Password**: `password123`
- **Quyền**: Quản trị toàn hệ thống
- **Truy cập**: Admin Dashboard, User Management, Product Management

### 👤 Tài khoản User thường
- **Username**: `user1`
- **Password**: `password123`
- **Họ tên**: Nguyễn Văn An
- **Quyền**: Mua sắm, quản lý giỏ hàng, đặt hàng

- **Username**: `user2`
- **Password**: `password123`
- **Họ tên**: Trần Thị Bình
- **Quyền**: Mua sắm, quản lý giỏ hàng, đặt hàng

## 📚 API Documentation

### 🔗 Swagger UI
Truy cập API documentation tại: **http://localhost:8080/swagger-ui.html**

### 📋 Các API endpoints chính

#### Authentication APIs
- `POST /api/v1/auth/login` - Đăng nhập
- `POST /api/v1/auth/register` - Đăng ký tài khoản
- `GET /api/v1/auth/me` - Thông tin user hiện tại

#### Product APIs
- `GET /api/v1/products` - Danh sách sản phẩm
- `GET /api/v1/products/{id}` - Chi tiết sản phẩm
- `GET /api/v1/products/search?keyword=` - Tìm kiếm sản phẩm
- `POST /api/v1/products` - Tạo sản phẩm (Admin)

#### Cart APIs
- `GET /api/v1/cart/details` - Chi tiết giỏ hàng
- `POST /api/v1/cart/add` - Thêm sản phẩm vào giỏ
- `PUT /api/v1/cart/update` - Cập nhật số lượng
- `DELETE /api/v1/cart/clear` - Xóa giỏ hàng

#### Order APIs
- `POST /api/v1/orders` - Tạo đơn hàng
- `GET /api/v1/orders` - Danh sách đơn hàng
- `GET /api/v1/orders/{id}` - Chi tiết đơn hàng

#### Configuration APIs
- `GET /api/v1/config/checkout` - Cấu hình thanh toán (phí ship, thuế)

## 🧪 Testing

### Chạy Unit Tests
```bash
# Chạy tất cả tests
./mvnw test

# Chạy tests với script tiện ích
./run-tests.sh

# Chạy test cụ thể
./mvnw test -Dtest=CartServiceImplTest
```

### Test Coverage
- **CartService**: 12 test cases (CRUD operations, exception handling)
- **ProductService**: 7 test cases (Product management, search)
- **Total**: 19+ unit tests với 100% success rate

## 📊 Tính năng chính

### 🛍️ Cho khách hàng
- **Duyệt sản phẩm**: Trang chủ, danh mục, tìm kiếm
- **Giỏ hàng thông minh**: Thêm/sửa/xóa sản phẩm real-time
- **Thanh toán linh hoạt**: COD, chuyển khoản, thẻ tín dụng
- **Quản lý đơn hàng**: Theo dõi trạng thái, lịch sử mua hàng
- **Tài khoản cá nhân**: Thông tin, địa chỉ, đổi mật khẩu

### 👨‍💼 Cho quản trị viên
- **Dashboard tổng quan**: Thống kê bán hàng, đơn hàng
- **Quản lý sản phẩm**: CRUD operations với Vue.js
- **Quản lý danh mục**: Tổ chức sản phẩm theo nhóm
- **Quản lý đơn hàng**: Xử lý, cập nhật trạng thái
- **Quản lý người dùng**: Phân quyền, khóa/mở tài khoản

## 🔧 Cấu hình nâng cao

### Environment Profiles
```bash
# Development (default)
./mvnw spring-boot:run

# Production
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Logging Configuration
- **Development**: Console + File logging với colors
- **Production**: File-only logging với rotation
- **Log files**: `logs/app.log`, `logs/error.log`

### Database Configuration
- **Development**: `ddl-auto=update`, SQL logging enabled
- **Production**: `ddl-auto=validate`, SQL logging disabled

## 🚀 Deployment

### Production Deployment
```bash
# Build production JAR
./mvnw clean package -Pprod

# Run with production settings
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

### Docker Deployment (Optional)
```bash
# Build Docker image
docker build -t java6-ecommerce .

# Run with Docker Compose
docker-compose -f docker-compose.prod.yml up -d
```

## 📈 Performance & Security

### Performance Features
- **Connection Pooling**: HikariCP với 20 max connections
- **Caching**: Static resource caching, template caching
- **Compression**: Response compression enabled
- **Async Logging**: Non-blocking file logging

### Security Features
- **Authentication**: Session-based với Spring Security
- **Authorization**: Role-based access control (ROLE_USER, ROLE_ADMIN)
- **Password Encryption**: BCrypt hashing
- **CSRF Protection**: Enabled cho forms, disabled cho APIs
- **Session Security**: HttpOnly, Secure cookies

## 🤝 Đóng góp và phát triển

### Development Workflow
1. Clone repository
2. Tạo feature branch
3. Implement changes với unit tests
4. Run test suite: `./run-tests.sh`
5. Submit pull request

### Code Quality
- **Unit Testing**: JUnit 5 + Mockito
- **Code Coverage**: 80%+ cho service layer
- **Documentation**: Swagger/OpenAPI
- **Logging**: Structured logging với Logback

## 📞 Liên hệ và hỗ trợ

### Thông tin dự án
- **Môn học**: Java 6 - Spring Boot Framework
- **Học viện**: FPT Polytechnic
- **Năm học**: 2024

### Tài liệu tham khảo
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Vue.js Guide](https://vuejs.org/guide/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Swagger/OpenAPI](https://swagger.io/docs/)

---

## 🎉 Kết luận

**Java 6 E-Commerce E-Store** là một dự án hoàn chỉnh thể hiện việc áp dụng thành công các công nghệ hiện đại trong phát triển ứng dụng web. Với kiến trúc Hybrid độc đáo, dự án đạt được sự cân bằng tối ưu giữa SEO và trải nghiệm người dùng, đồng thời đảm bảo tính bảo mật, hiệu suất và khả năng mở rộng.

**Các điểm nổi bật:**
- ✅ Kiến trúc Hybrid: SEO + SPA
- ✅ API-First với Swagger documentation
- ✅ Unit Testing với 100% success rate
- ✅ Production-ready với logging và monitoring
- ✅ Security best practices
- ✅ Modern tech stack (Java 21, Spring Boot 3, Vue 3)

**Status: READY FOR PRODUCTION** 🚀