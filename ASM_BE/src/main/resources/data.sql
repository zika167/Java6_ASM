-- Sample data for Java 6 E-Commerce Application
-- This file will be executed automatically by Spring Boot on startup

-- Insert sample categories
INSERT IGNORE INTO categories (id, name, is_active) VALUES 
(1, 'Điện tử', true),
(2, 'Thời trang', true),
(3, 'Gia dụng', true),
(4, 'Sách & Văn phòng phẩm', true),
(5, 'Thể thao & Du lịch', true);

-- Insert sample users (password is 'password123' encoded with BCrypt)
INSERT IGNORE INTO users (id, username, password, fullname, email, phone, role) VALUES 
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Quản trị viên', 'admin@estore.com', '0901234567', 'ROLE_ADMIN'),
(2, 'user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Nguyễn Văn An', 'user1@example.com', '0987654321', 'ROLE_USER'),
(3, 'user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Trần Thị Bình', 'user2@example.com', '0912345678', 'ROLE_USER');

-- Insert sample products
INSERT IGNORE INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(1, 'iPhone 15 Pro Max', 29990000, 'Điện thoại thông minh cao cấp với chip A17 Pro, camera 48MP và màn hình Super Retina XDR 6.7 inch', 'iphone-15-pro-max.jpg', 1, true),
(2, 'Samsung Galaxy S24 Ultra', 27990000, 'Smartphone Android flagship với bút S Pen, camera 200MP và màn hình Dynamic AMOLED 2X', 'samsung-s24-ultra.jpg', 1, true),
(3, 'MacBook Air M3', 27990000, 'Laptop siêu mỏng với chip M3, màn hình Liquid Retina 13.6 inch và thời lượng pin 18 giờ', 'macbook-air-m3.jpg', 1, true),
(4, 'Dell XPS 13', 25990000, 'Ultrabook cao cấp với màn hình InfinityEdge, bộ xử lý Intel Core i7 thế hệ 13', 'dell-xps-13.jpg', 1, true),
(5, 'Sony WH-1000XM5', 7990000, 'Tai nghe chống ồn cao cấp với chất lượng âm thanh Hi-Res và thời lượng pin 30 giờ', 'sony-wh1000xm5.jpg', 1, true),
(6, 'Áo sơ mi nam công sở', 299000, 'Áo sơ mi nam chất liệu cotton cao cấp, phù hợp cho môi trường công sở', 'ao-so-mi-nam.jpg', 2, true),
(7, 'Váy đầm nữ dự tiệc', 599000, 'Váy đầm nữ thiết kế thanh lịch, phù hợp cho các buổi tiệc và sự kiện', 'vay-dam-nu.jpg', 2, true),
(8, 'Nồi cơm điện Panasonic', 1299000, 'Nồi cơm điện cao cấp với công nghệ nấu áp suất, dung tích 1.8L', 'noi-com-dien.jpg', 3, true),
(9, 'Máy xay sinh tố Philips', 899000, 'Máy xay sinh tố đa năng với công suất 1000W và cối xay inox', 'may-xay-sinh-to.jpg', 3, true),
(10, 'Sách lập trình Java', 199000, 'Sách hướng dẫn lập trình Java từ cơ bản đến nâng cao, phiên bản mới nhất', 'sach-java.jpg', 4, true);