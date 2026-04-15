# 🛒 Java 6 E-Commerce E-Store

> **Dự án thương mại điện tử hiện đại với kiến trúc Hybrid độc đáo - Kết hợp Thymeleaf (SEO) và Vue.js (Dynamic UX)**

## 🚀 Khởi chạy nhanh (Quick Start)

```bash
# 1. Clone dự án
git clone <repository-url>
cd Java6_ASM/ASM_BE

# 2. Khởi động database (Docker)
docker-compose up -d

# 3. Chạy ứng dụng
./mvnw spring-boot:run

# 4. Truy cập ứng dụng
# Trang chủ: http://localhost:8080
# Admin: http://localhost:8080/admin (admin/password123)
```

**Yêu cầu:** Java 17+, Maven 3.6+, Docker

---

## 📑 Mục lục

- [Giới thiệu dự án](#-giới-thiệu-dự-án)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Kiến trúc Hybrid](#-kiến-trúc-hybrid-độc-đáo)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Hướng dẫn cài đặt](#️-hướng-dẫn-cài-đặt-và-chạy-dự-án)
  - [Yêu cầu hệ thống](#️-yêu-cầu-hệ-thống)
  - [Khởi động Database](#️-bước-2-khởi-động-mariadb-database)
  - [Chạy ứng dụng](#-bước-3-build-và-chạy-ứng-dụng)
  - [Xử lý lỗi](#-xử-lý-lỗi-thường-gặp)
- [Tài khoản mẫu](#-tài-khoản-đăng-nhập-mẫu)
- [API Documentation](#-api-documentation)
- [Cấu trúc Database](#-cấu-trúc-database)
- [Tính năng chính](#-tính-năng-chính)
- [Testing](#-testing)
- [Đóng góp và phát triển](#-đóng-góp-và-phát-triển)
- [Deployment](#-deployment)
- [Liên hệ](#-liên-hệ-và-hỗ-trợ)

---

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
- **Java 17** - Ngôn ngữ lập trình chính (tương thích Java 21)
- **Spring Boot 3.2.4** - Framework chính cho backend development
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

Dự án áp dụng kiến trúc **Hybrid** kết hợp giữa Server-Side Rendering (Thymeleaf) và Client-Side Rendering (Vue.js) để tối ưu hóa cả SEO và trải nghiệm người dùng.

### 🌟 Nguyên tắc phân chia Frontend/Backend

#### 📄 Server-Side Rendering với Thymeleaf (Tối ưu SEO)
**Sử dụng cho:**
- ✅ Trang chủ (Home page)
- ✅ Danh sách sản phẩm (Product listing)
- ✅ Chi tiết sản phẩm (Product detail)
- ✅ Các trang tĩnh (About, Contact)

**Lý do:**
- Tốc độ tải trang nhanh (First Contentful Paint)
- SEO-friendly (Search engine có thể crawl nội dung)
- Meta tags và structured data tối ưu
- Render HTML hoàn chỉnh từ server

**Cách hoạt động:**
```
User Request → Spring MVC Controller → Service → Repository → Database
                      ↓
              Thymeleaf Template Engine
                      ↓
              HTML Response (SEO-ready)
```

#### ⚡ Client-Side Rendering với Vue.js + Axios (Trải nghiệm động)
**Sử dụng cho:**
- ✅ Giỏ hàng (Shopping Cart)
- ✅ Thanh toán (Checkout)
- ✅ Admin Dashboard
- ✅ Quản lý sản phẩm, đơn hàng, người dùng

**Lý do:**
- Tương tác real-time không cần reload trang
- UX mượt mà và responsive
- Giảm tải cho server (chỉ trả về JSON)
- Phù hợp cho các tính năng CRUD phức tạp

**Cách hoạt động:**
```
User Action → Vue.js Component → Axios HTTP Request → REST API
                                                          ↓
                                                    JSON Response
                                                          ↓
                                              Vue.js Update DOM
```

### 🎯 Điểm nổi bật của kiến trúc

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

### 📐 Quy tắc tổ chức code (Backend)

**Mô hình chuẩn:** `Controller → Service → Repository → Entity`

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
├──────────────────────┬──────────────────────────────────┤
│  @Controller         │  @RestController                 │
│  (Thymeleaf Views)   │  (JSON APIs)                     │
│  /products           │  /api/v1/products                │
└──────────────────────┴──────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                         │
│  @Service - Business Logic                              │
│  ProductService, CartService, OrderService              │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                  REPOSITORY LAYER                        │
│  @Repository - Data Access                              │
│  ProductRepository, CartRepository (JPA)                │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                    ENTITY LAYER                          │
│  @Entity - Domain Models                                │
│  Product, Cart, Order, User                             │
└─────────────────────────────────────────────────────────┘
```

**Quy định:**
- Các API trả về JSON phải nằm trong `@RestController` (ví dụ: `/api/v1/...`)
- Các trang render bằng Thymeleaf dùng `@Controller`
- Format Response API chuẩn:
```json
{
  "status": 200,
  "message": "Success",
  "data": {...}
}
```

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

### ⚙️ Yêu cầu hệ thống
- **Java 17** hoặc cao hơn (dự án sử dụng Java 17)
- **Maven 3.6+** (hoặc sử dụng Maven Wrapper đi kèm)
- **Docker & Docker Compose** (để chạy MariaDB)
- **Git** (để clone dự án)
- **IDE khuyến nghị**: IntelliJ IDEA, Eclipse, hoặc VS Code với Java Extension Pack

### 📥 Bước 1: Clone dự án
```bash
# Clone repository về máy
git clone <repository-url>
cd Java6_ASM
```

### 🗄️ Bước 2: Khởi động MariaDB Database

#### Cách 1: Sử dụng Docker Compose (Khuyến nghị)
```bash
# Di chuyển vào thư mục backend
cd ASM_BE

# Khởi động MariaDB container
docker-compose up -d

# Kiểm tra container đang chạy
docker ps

# Kiểm tra logs của database (nếu cần)
docker-compose logs -f mariadb

# Dừng database khi không dùng
docker-compose down

# Xóa database và volume (reset toàn bộ dữ liệu)
docker-compose down -v
```

**Thông tin kết nối database:**
- **Host**: `localhost:3306`
- **Database**: `java6_estore`
- **Username**: `estore_user`
- **Password**: `estore_password`
- **Root Password**: `root_password`

#### Cách 2: Cài đặt MariaDB trực tiếp (Không dùng Docker)
Nếu bạn không muốn dùng Docker, có thể cài đặt MariaDB trực tiếp:

**Windows:**
1. Download MariaDB từ https://mariadb.org/download/
2. Cài đặt và thiết lập root password
3. Tạo database và user:
```sql
CREATE DATABASE java6_estore;
CREATE USER 'estore_user'@'localhost' IDENTIFIED BY 'estore_password';
GRANT ALL PRIVILEGES ON java6_estore.* TO 'estore_user'@'localhost';
FLUSH PRIVILEGES;
```

**macOS (sử dụng Homebrew):**
```bash
brew install mariadb
brew services start mariadb
mysql -u root
# Sau đó chạy các lệnh SQL ở trên
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install mariadb-server
sudo systemctl start mariadb
sudo mysql
# Sau đó chạy các lệnh SQL ở trên
```

**Lưu ý quan trọng**: 
- Database sẽ được tự động khởi tạo schema và dữ liệu mẫu khi ứng dụng chạy lần đầu tiên
- File `schema.sql` sẽ tạo các bảng (users, products, categories, orders, cart, v.v.)
- File `data.sql` sẽ insert dữ liệu mẫu (5 users, 23 products, 8 categories, 5 orders)

### 🚀 Bước 3: Build và chạy ứng dụng

#### Cách 1: Chạy trực tiếp với Maven (Development Mode)
```bash
# Đảm bảo đang ở thư mục ASM_BE
cd ASM_BE

# Chạy ứng dụng với Maven Wrapper (khuyến nghị)
./mvnw spring-boot:run

# Hoặc nếu đã cài Maven global
mvn spring-boot:run

# Chạy với profile cụ thể
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

**Trên Windows, sử dụng:**
```cmd
mvnw.cmd spring-boot:run
```

#### Cách 2: Build JAR và chạy (Production Mode)
```bash
# Build project thành JAR file
./mvnw clean package

# Chạy JAR file
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar

# Chạy với production profile
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# Chạy với port tùy chỉnh
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar --server.port=9090
```

#### Cách 3: Chạy từ IDE

**IntelliJ IDEA:**
1. Mở project: `File > Open` → chọn thư mục `ASM_BE`
2. Đợi Maven import dependencies
3. Tìm file `AsmBeApplication.java`
4. Click chuột phải → `Run 'AsmBeApplication'`

**Eclipse:**
1. Import project: `File > Import > Existing Maven Projects`
2. Chọn thư mục `ASM_BE`
3. Đợi Maven build workspace
4. Chuột phải vào `AsmBeApplication.java` → `Run As > Java Application`

**VS Code:**
1. Mở thư mục `ASM_BE`
2. Cài đặt Extension Pack for Java
3. Mở `AsmBeApplication.java`
4. Click `Run` hoặc `Debug` ở trên main method

### ✅ Bước 4: Kiểm tra ứng dụng đã chạy thành công

#### Kiểm tra Console Output
Khi ứng dụng khởi động thành công, bạn sẽ thấy log tương tự:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

...
Started AsmBeApplication in 5.234 seconds (process running for 5.789)
```

#### Truy cập các URL sau để kiểm tra:

**🏠 Trang chủ (Thymeleaf - SEO Optimized):**
- URL: http://localhost:8080
- Mô tả: Trang chủ với danh sách sản phẩm render từ server

**🔐 Trang đăng nhập:**
- URL: http://localhost:8080/auth/login
- Mô tả: Form đăng nhập với Spring Security

**🔑 Trang đăng ký:**
- URL: http://localhost:8080/auth/register
- Mô tả: Form đăng ký tài khoản mới

**📦 Danh sách sản phẩm:**
- URL: http://localhost:8080/products
- Mô tả: Trang danh sách sản phẩm với phân trang

**🛒 Giỏ hàng (Vue.js - Dynamic):**
- URL: http://localhost:8080/cart
- Mô tả: Giỏ hàng tương tác với Vue.js + Axios

**💳 Thanh toán (Vue.js - Dynamic):**
- URL: http://localhost:8080/checkout
- Mô tả: Trang thanh toán với tính toán động

**👨‍💼 Admin Dashboard (Vue.js - SPA):**
- URL: http://localhost:8080/admin
- Mô tả: Trang quản trị với Vue.js (yêu cầu đăng nhập ROLE_ADMIN)

**📚 API Documentation (Swagger UI):**
- URL: http://localhost:8080/swagger-ui.html
- Mô tả: Tài liệu API tương tác (nếu đã cấu hình Swagger)

**🔌 Health Check API:**
- URL: http://localhost:8080/api/v1/config/checkout
- Mô tả: API test để kiểm tra backend hoạt động

### 🐛 Xử lý lỗi thường gặp

#### Lỗi 1: Port 8080 đã được sử dụng
```
***************************
APPLICATION FAILED TO START
***************************
Description:
Web server failed to start. Port 8080 was already in use.
```

**Giải pháp:**
```bash
# Cách 1: Thay đổi port trong application.properties
server.port=9090

# Cách 2: Chạy với port khác
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=9090

# Cách 3: Kill process đang dùng port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -ti:8080 | xargs kill -9
```

#### Lỗi 2: Không kết nối được database
```
java.sql.SQLNonTransientConnectionException: Could not connect to address=(host=localhost)(port=3306)
```

**Giải pháp:**
```bash
# Kiểm tra MariaDB container đang chạy
docker ps

# Nếu không thấy, khởi động lại
docker-compose up -d

# Kiểm tra logs
docker-compose logs mariadb

# Test kết nối database
docker exec -it java6_mariadb mysql -u estore_user -p
# Nhập password: estore_password
```

#### Lỗi 3: Maven build failed
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Giải pháp:**
```bash
# Clean và rebuild
./mvnw clean install

# Nếu vẫn lỗi, xóa cache Maven
rm -rf ~/.m2/repository
./mvnw clean install

# Kiểm tra Java version
java -version  # Phải là Java 17 hoặc cao hơn
```

#### Lỗi 4: Lombok không hoạt động
```
[ERROR] cannot find symbol: method builder()
```

**Giải pháp:**
- **IntelliJ IDEA**: Cài đặt Lombok Plugin và enable Annotation Processing
  - `Settings > Plugins > Marketplace > Search "Lombok" > Install`
  - `Settings > Build > Compiler > Annotation Processors > Enable annotation processing`
- **Eclipse**: Cài đặt Lombok từ https://projectlombok.org/download
- **VS Code**: Cài đặt extension "Lombok Annotations Support"

### 🔄 Khởi động lại và dừng ứng dụng

```bash
# Dừng ứng dụng: Nhấn Ctrl + C trong terminal

# Dừng database
docker-compose down

# Khởi động lại toàn bộ
docker-compose up -d
./mvnw spring-boot:run

# Xem logs real-time
docker-compose logs -f mariadb
```

## 👥 Tài khoản đăng nhập mẫu

### 🔑 Tài khoản Administrator
- **Username**: `admin`
- **Password**: `password123`
- **Họ tên**: Quản trị viên hệ thống
- **Email**: admin@estore.com
- **Quyền**: Quản trị toàn hệ thống
- **Truy cập**: Admin Dashboard, User Management, Product Management

### 👨‍💼 Tài khoản Manager
- **Username**: `manager`
- **Password**: `password123`
- **Họ tên**: Phạm Thị Dung
- **Email**: manager@estore.com
- **Quyền**: Quản lý sản phẩm và đơn hàng

### 👤 Tài khoản User thường
- **Username**: `user1`
- **Password**: `password123`
- **Họ tên**: Nguyễn Văn An
- **Email**: user1@example.com
- **Quyền**: Mua sắm, quản lý giỏ hàng, đặt hàng

- **Username**: `user2`
- **Password**: `password123`
- **Họ tên**: Trần Thị Bình
- **Email**: user2@example.com
- **Quyền**: Mua sắm, quản lý giỏ hàng, đặt hàng

- **Username**: `user3`
- **Password**: `password123`
- **Họ tên**: Lê Minh Cường
- **Email**: user3@example.com
- **Quyền**: Mua sắm, quản lý giỏ hàng, đặt hàng

### 📊 Dữ liệu mẫu có sẵn
- **8 danh mục sản phẩm**: Điện tử, Thời trang, Gia dụng, Sách & Văn phòng phẩm, v.v.
- **23 sản phẩm**: iPhone, MacBook, Samsung, Nike, Panasonic, v.v.
- **3 giỏ hàng có sản phẩm**: Sẵn sàng để test chức năng checkout
- **5 đơn hàng mẫu**: Với các trạng thái khác nhau (Pending, Confirmed, Shipping, Delivered)

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

## 📊 Cấu trúc Database

### 🗃️ Database Schema

Dự án sử dụng **MariaDB** với 7 bảng chính được thiết kế theo chuẩn quan hệ:

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│    users    │────────<│    carts     │>────────│ cart_items  │
│             │         │              │         │             │
│ - id (PK)   │         │ - id (PK)    │         │ - id (PK)   │
│ - username  │         │ - user_id(FK)│         │ - cart_id   │
│ - password  │         └──────────────┘         │ - product_id│
│ - role      │                                  │ - quantity  │
└─────────────┘                                  └─────────────┘
       │                                                │
       │                                                │
       │         ┌──────────────┐                       │
       └────────<│    orders    │                       │
                 │              │                       │
                 │ - id (PK)    │                       │
                 │ - user_id(FK)│                       │
                 │ - status     │                       │
                 │ - total      │                       │
                 └──────────────┘                       │
                        │                               │
                        │                               │
                        v                               │
                 ┌──────────────┐                       │
                 │order_details │                       │
                 │              │                       │
                 │ - id (PK)    │                       │
                 │ - order_id   │                       │
                 │ - product_id │<──────────────────────┘
                 │ - quantity   │                       
                 │ - price      │                       
                 └──────────────┘                       
                        │                               
                        │                               
                        v                               
                 ┌──────────────┐         ┌─────────────┐
                 │   products   │>────────│ categories  │
                 │              │         │             │
                 │ - id (PK)    │         │ - id (PK)   │
                 │ - name       │         │ - name      │
                 │ - price      │         │ - is_active │
                 │ - category_id│         └─────────────┘
                 │ - is_active  │
                 └──────────────┘
```

### 📋 Chi tiết các bảng

#### 1. **users** - Quản lý người dùng
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- username: VARCHAR(50) UNIQUE
- password: VARCHAR(255) - BCrypt hashed
- full_name: VARCHAR(100)
- email: VARCHAR(255) UNIQUE
- phone: VARCHAR(20)
- role: ENUM('ROLE_USER', 'ROLE_ADMIN')
- created_at, updated_at: TIMESTAMP
```

#### 2. **categories** - Danh mục sản phẩm
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- name: VARCHAR(100) UNIQUE
- is_active: BOOLEAN
- created_at, updated_at: TIMESTAMP
```

#### 3. **products** - Sản phẩm
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- name: VARCHAR(200)
- price: DECIMAL(10,2)
- description: TEXT
- image: VARCHAR(500)
- category_id: BIGINT (FK → categories)
- is_active: BOOLEAN
- created_at, updated_at: TIMESTAMP
```

#### 4. **carts** - Giỏ hàng
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- user_id: BIGINT (FK → users, UNIQUE)
- created_at, updated_at: TIMESTAMP
```

#### 5. **cart_items** - Chi tiết giỏ hàng
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- cart_id: BIGINT (FK → carts)
- product_id: BIGINT (FK → products)
- quantity: INT
- UNIQUE(cart_id, product_id)
```

#### 6. **orders** - Đơn hàng
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- user_id: BIGINT (FK → users)
- order_date: TIMESTAMP
- total_amount: DECIMAL(10,2)
- status: ENUM('PENDING', 'CONFIRMED', 'SHIPPING', 'DELIVERED', 'CANCELLED')
- address: TEXT
- updated_at: TIMESTAMP
```

#### 7. **order_details** - Chi tiết đơn hàng
```sql
- id: BIGINT (PK, AUTO_INCREMENT)
- order_id: BIGINT (FK → orders)
- product_id: BIGINT (FK → products)
- quantity: INT
- price: DECIMAL(10,2) - Giá tại thời điểm đặt hàng
- created_at: TIMESTAMP
```

### 🔐 Ràng buộc và Index

**Foreign Keys:**
- `ON DELETE CASCADE`: cart_items, order_details (xóa cart/order → xóa items)
- `ON DELETE RESTRICT`: products, orders (không cho xóa nếu còn tham chiếu)

**Indexes để tối ưu performance:**
- `idx_username`, `idx_email` trên users
- `idx_category`, `idx_active`, `idx_price` trên products
- `FULLTEXT idx_search` trên products(name, description) - Tìm kiếm full-text
- `idx_user`, `idx_status`, `idx_order_date` trên orders

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

### 🎨 Quy trình chuyển đổi Frontend (ASM_FE → ASM_BE)

Dự án ban đầu có thư mục `ASM_FE` chứa các file HTML tĩnh. Quy trình chuyển đổi sang kiến trúc Hybrid:

#### Bước 1: Di chuyển Static Resources
```bash
# CSS, JS, Images từ ASM_FE → ASM_BE/src/main/resources/static/
ASM_FE/css/       → ASM_BE/src/main/resources/static/css/
ASM_FE/js/        → ASM_BE/src/main/resources/static/js/
ASM_FE/images/    → ASM_BE/src/main/resources/static/images/
ASM_FE/fonts/     → ASM_BE/src/main/resources/static/fonts/
```

#### Bước 2: Chuyển đổi HTML sang Thymeleaf
```bash
# HTML files từ ASM_FE → ASM_BE/src/main/resources/templates/
ASM_FE/index.html       → templates/index.html
ASM_FE/products.html    → templates/products.html
ASM_FE/product-detail.html → templates/product-detail.html
```

**Thay đổi cần thiết trong HTML:**
```html
<!-- Trước (Static HTML) -->
<link rel="stylesheet" href="css/main.css">
<script src="js/main.js"></script>
<img src="images/product.jpg">

<!-- Sau (Thymeleaf) -->
<link rel="stylesheet" th:href="@{/css/main.css}">
<script th:src="@{/js/main.js}"></script>
<img th:src="@{/images/product.jpg}">
```

#### Bước 3: Tách Layout chung với Thymeleaf Fragments

**Tạo file layout.html:**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:fragment="head">
    <meta charset="UTF-8">
    <title th:text="${title}">E-Store</title>
    <link rel="stylesheet" th:href="@{/css/main.css}">
</head>
<body>
    <!-- Header Fragment -->
    <header th:fragment="header">
        <nav>...</nav>
    </header>
    
    <!-- Footer Fragment -->
    <footer th:fragment="footer">
        <p>&copy; 2024 E-Store</p>
    </footer>
</body>
</html>
```

**Sử dụng fragments trong các trang:**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="~{layout :: head}"></head>
<body>
    <header th:replace="~{layout :: header}"></header>
    
    <main>
        <!-- Nội dung trang -->
    </main>
    
    <footer th:replace="~{layout :: footer}"></footer>
</body>
</html>
```

#### Bước 4: Tích hợp Vue.js cho trang động

**Trang giỏ hàng (cart.html):**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="~{layout :: head}"></head>
<body>
    <div id="app">
        <h1>Giỏ hàng</h1>
        <div v-for="item in cartItems" :key="item.id">
            <p>{{ item.productName }} - {{ item.quantity }}</p>
        </div>
    </div>
    
    <!-- Vue.js CDN -->
    <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    
    <script>
        const { createApp } = Vue;
        
        createApp({
            data() {
                return {
                    cartItems: []
                }
            },
            mounted() {
                this.loadCart();
            },
            methods: {
                async loadCart() {
                    const response = await axios.get('/api/v1/cart/details');
                    this.cartItems = response.data.data.items;
                }
            }
        }).mount('#app');
    </script>
</body>
</html>
```

#### Bước 5: Cấu hình Static Resources trong Spring Boot

**WebConfig.java:**
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
```

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

### 📜 Quy tắc phát triển (Development Rules)

**⚠️ BẮT BUỘC: Đọc file `claude.md` trước khi phát triển!**

File `claude.md` chứa toàn bộ ngữ cảnh dự án, quy tắc phát triển và kiến trúc. Mọi developer phải đọc và tuân thủ các quy tắc trong file này.

#### 🎯 Nguyên tắc cốt lõi

1. **Kiến trúc FE/BE phải tuân thủ:**
   - ✅ Danh sách sản phẩm, chi tiết sản phẩm → Thymeleaf (SEO)
   - ✅ Giỏ hàng, đặt hàng, Admin → Vue.js + Axios (Dynamic)

2. **Tổ chức code Backend:**
   - Mô hình chuẩn: `Controller → Service → Repository → Entity`
   - API JSON: `@RestController` với prefix `/api/v1/...`
   - View Thymeleaf: `@Controller` không có prefix `/api`

3. **Security bắt buộc:**
   - Sử dụng Spring Security
   - Phân quyền: `ROLE_USER` và `ROLE_ADMIN`
   - Password: BCrypt hashing
   - CSRF protection cho forms

4. **Format Response API chuẩn:**
```json
{
  "status": 200,
  "message": "Success",
  "data": {...}
}
```

### 🔄 Development Workflow

#### Bước 1: Setup môi trường
```bash
# Clone và setup
git clone <repository-url>
cd Java6_ASM/ASM_BE

# Khởi động database
docker-compose up -d

# Chạy ứng dụng
./mvnw spring-boot:run
```

#### Bước 2: Tạo feature branch
```bash
# Tạo branch mới từ main
git checkout -b feature/ten-tinh-nang

# Hoặc bugfix
git checkout -b bugfix/ten-loi
```

#### Bước 3: Phát triển tính năng

**Thêm Entity mới:**
```java
@Entity
@Table(name = "table_name")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ... fields
}
```

**Thêm Repository:**
```java
@Repository
public interface MyRepository extends JpaRepository<MyEntity, Long> {
    // Custom queries
}
```

**Thêm Service:**
```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final MyRepository repository;
    
    // Business logic
}
```

**Thêm Controller (Thymeleaf):**
```java
@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService service;
    
    @GetMapping("/my-page")
    public String showPage(Model model) {
        model.addAttribute("data", service.getData());
        return "my-page"; // templates/my-page.html
    }
}
```

**Thêm REST Controller (API):**
```java
@RestController
@RequestMapping("/api/v1/my-resource")
@RequiredArgsConstructor
public class MyApiController {
    private final MyService service;
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.ok(
            new ApiResponse(200, "Success", service.getAll())
        );
    }
}
```

#### Bước 4: Viết Unit Tests
```java
@SpringBootTest
class MyServiceTest {
    @Mock
    private MyRepository repository;
    
    @InjectMocks
    private MyService service;
    
    @Test
    void testGetAll() {
        // Arrange
        when(repository.findAll()).thenReturn(mockData);
        
        // Act
        List<MyEntity> result = service.getAll();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
```

#### Bước 5: Chạy tests
```bash
# Chạy tất cả tests
./mvnw test

# Chạy test cụ thể
./mvnw test -Dtest=MyServiceTest

# Chạy với script
./run-tests.sh
```

#### Bước 6: Commit và Push
```bash
# Add changes
git add .

# Commit với message rõ ràng
git commit -m "feat: thêm chức năng quản lý X"

# Push lên remote
git push origin feature/ten-tinh-nang
```

#### Bước 7: Tạo Pull Request
1. Mở GitHub/GitLab
2. Tạo Pull Request từ feature branch → main
3. Mô tả chi tiết thay đổi
4. Request review từ team members
5. Đợi approval và merge

### 🧪 Testing Guidelines

#### Unit Testing Requirements
- **Coverage tối thiểu**: 80% cho service layer
- **Framework**: JUnit 5 + Mockito
- **Naming convention**: `methodName_scenario_expectedResult`

#### Test Structure
```java
@Test
void addToCart_validProduct_shouldAddSuccessfully() {
    // Given (Arrange)
    Long userId = 1L;
    Long productId = 1L;
    
    // When (Act)
    CartDTO result = cartService.addToCart(userId, productId, 1);
    
    // Then (Assert)
    assertNotNull(result);
    assertEquals(1, result.getItems().size());
}
```

### 📝 Code Quality Standards

#### Naming Conventions
- **Classes**: PascalCase (`ProductService`, `UserController`)
- **Methods**: camelCase (`getProductById`, `addToCart`)
- **Variables**: camelCase (`productList`, `userId`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_CART_ITEMS`, `DEFAULT_PAGE_SIZE`)

#### Code Documentation
```java
/**
 * Thêm sản phẩm vào giỏ hàng
 * 
 * @param userId ID của người dùng
 * @param productId ID của sản phẩm
 * @param quantity Số lượng sản phẩm
 * @return CartDTO chứa thông tin giỏ hàng đã cập nhật
 * @throws ResourceNotFoundException nếu user hoặc product không tồn tại
 */
public CartDTO addToCart(Long userId, Long productId, Integer quantity) {
    // Implementation
}
```

### 🔍 Code Review Checklist

- [ ] Code tuân thủ kiến trúc Hybrid (Thymeleaf vs Vue.js)
- [ ] API response đúng format chuẩn
- [ ] Có unit tests với coverage >= 80%
- [ ] Không có hardcoded values (dùng configuration)
- [ ] Exception handling đầy đủ
- [ ] Security: Authorization checks cho admin endpoints
- [ ] Logging đầy đủ (INFO, ERROR levels)
- [ ] Database queries được optimize (indexes, N+1 problem)
- [ ] Code comments cho logic phức tạp
- [ ] No sensitive data in logs (passwords, tokens)

### 🚀 Deployment Checklist

- [ ] All tests passing (`./mvnw test`)
- [ ] Build successful (`./mvnw clean package`)
- [ ] Database migrations tested
- [ ] Environment variables configured
- [ ] Logging configured for production
- [ ] Security settings reviewed
- [ ] Performance tested (load testing)
- [ ] Backup strategy in place

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