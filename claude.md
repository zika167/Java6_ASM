PROJECT CONTEXT: JAVA 6 ASSIGNMENT (E-COMMERCE)

1. TỔNG QUAN DỰ ÁN (PROJECT OVERVIEW)

Tên dự án: Java 6 E-Commerce E-Store

Mục tiêu: Xây dựng website bán hàng theo mô hình Hybrid (Thymeleaf cho SEO + VueJS/Axios cho tính năng động) kết hợp RESTful API.

Tech Stack:

Backend: Java 21, Spring Boot 3.x, Spring MVC, RESTful API, Spring Security, Spring Data JPA.

Frontend: HTML/CSS/JS có sẵn, Thymeleaf (Render giao diện), Vue 3 (CDN hoặc Component), Axios.

Database: MariaDB.

Tools: Lombok, Maven, Docker.

2. QUY TẮC PHÁT TRIỂN (DEVELOPMENT RULES) - BẮT BUỘC AI PHẢI TUÂN THỦ

LUÔN LUÔN đọc file claude.md này trước khi sinh code để hiểu ngữ cảnh.

Kiến trúc FE/BE: - Phần danh sách sản phẩm, chi tiết sản phẩm: Sử dụng Spring Boot MVC + Thymeleaf để render HTML từ server (Tối ưu SEO).

Phần Giỏ hàng, Đặt hàng, Admin Dashboard, Quản lý: Sử dụng VueJS + Axios gọi RESTful API từ Spring Boot.

Quy tắc tổ chức code (Backend): Mô hình chuẩn Controller -> Service -> Repository -> Entity. Các API trả về JSON phải nằm trong package @RestController (ví dụ: /api/v1/...). Các trang render bằng Thymeleaf dùng @Controller.

Quy định Security: Bắt buộc dùng Spring Security. Phân quyền ROLE_USER và ROLE_ADMIN. Token (JWT) hoặc Session tùy chọn nhưng phải bảo mật các route theo yêu cầu.

Format Response API: Mọi REST API phải trả về format chuẩn: { "status": 200, "message": "Success", "data": {...} }.

3. CẤU TRÚC DATABASE DỰ KIẾN (ENTITIES)

User (id, username, password, fullname, email, phone, role)

Category (id, name, is_active)

Product (id, name, price, description, image, category_id, is_active)

Order (id, user_id, order_date, total_amount, status, address)

OrderDetail (id, order_id, product_id, quantity, price)

4. QUY TRÌNH CHUYỂN ĐỔI FRONTEND HIỆN TẠI

Thư mục ASM_FE đang chứa các file HTML tĩnh tĩnh. Quy trình chuyển đổi:

Các file tĩnh (CSS, JS, Images) chuyển vào ASM_BE/src/main/resources/static/.

Các trang HTML chuyển vào ASM_BE/src/main/resources/templates/.

Tách Layout chung (Header, Footer, Sidebar) bằng Thymeleaf fragment (th:fragment).

Tại các trang cần tương tác (Giỏ hàng, Checkout, Admin), khởi tạo Vue App (Vue.createApp({...}).mount('#app')).