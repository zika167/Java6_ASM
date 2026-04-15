-- Sample data for Java 6 E-Commerce Application
-- This file will be executed automatically by Spring Boot after schema creation

-- Insert sample categories
INSERT INTO categories (id, name, is_active) VALUES 
(1, 'Điện tử', true),
(2, 'Thời trang', true),
(3, 'Gia dụng', true),
(4, 'Sách & Văn phòng phẩm', true),
(5, 'Thể thao & Du lịch', true),
(6, 'Làm đẹp & Sức khỏe', true),
(7, 'Mẹ & Bé', true),
(8, 'Xe cộ & Phụ kiện', true);

-- Insert sample users (password is 'password123' encoded with BCrypt)
INSERT INTO users (id, username, password, full_name, email, phone, role) VALUES 
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Quản trị viên hệ thống', 'admin@estore.com', '0901234567', 'ROLE_ADMIN'),
(2, 'user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Nguyễn Văn An', 'user1@example.com', '0987654321', 'ROLE_USER'),
(3, 'user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Trần Thị Bình', 'user2@example.com', '0912345678', 'ROLE_USER'),
(4, 'user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Lê Minh Cường', 'user3@example.com', '0923456789', 'ROLE_USER'),
(5, 'manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jS6PyraXwmvO', 'Phạm Thị Dung', 'manager@estore.com', '0934567890', 'ROLE_ADMIN');

-- Insert sample products for Electronics category
INSERT INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(1, 'iPhone 15 Pro Max 256GB', 29990000, 'Điện thoại thông minh cao cấp với chip A17 Pro, camera 48MP chính và hệ thống camera Pro với zoom quang học 5x. Màn hình Super Retina XDR 6.7 inch với Dynamic Island.', 'iphone-15-pro-max.jpg', 1, true),
(2, 'Samsung Galaxy S24 Ultra 512GB', 27990000, 'Smartphone Android flagship với bút S Pen tích hợp, camera 200MP chính và màn hình Dynamic AMOLED 2X 6.8 inch. Chip Snapdragon 8 Gen 3 mạnh mẽ.', 'samsung-s24-ultra.jpg', 1, true),
(3, 'MacBook Air M3 13 inch', 27990000, 'Laptop siêu mỏng với chip M3 8-core CPU, màn hình Liquid Retina 13.6 inch và thời lượng pin lên đến 18 giờ. RAM 8GB, SSD 256GB.', 'macbook-air-m3.jpg', 1, true),
(4, 'Dell XPS 13 Plus', 25990000, 'Ultrabook cao cấp với màn hình InfinityEdge 13.4 inch, bộ xử lý Intel Core i7-13700H, RAM 16GB, SSD 512GB. Thiết kế premium với bàn phím cảm ứng.', 'dell-xps-13.jpg', 1, true),
(5, 'Sony WH-1000XM5', 7990000, 'Tai nghe chống ồn cao cấp với công nghệ V1 processor, chất lượng âm thanh Hi-Res và thời lượng pin 30 giờ. Hỗ trợ LDAC và 360 Reality Audio.', 'sony-wh1000xm5.jpg', 1, true),
(6, 'iPad Pro 12.9 inch M2', 24990000, 'Máy tính bảng chuyên nghiệp với chip M2, màn hình Liquid Retina XDR 12.9 inch, hỗ trợ Apple Pencil thế hệ 2 và Magic Keyboard.', 'ipad-pro-m2.jpg', 1, true),
(7, 'AirPods Pro 2nd Gen', 5990000, 'Tai nghe không dây với chip H2, chống ồn chủ động thế hệ mới, âm thanh không gian cá nhân hóa và hộp sạc MagSafe.', 'airpods-pro-2.jpg', 1, true),
(8, 'Apple Watch Series 9', 8990000, 'Đồng hồ thông minh với chip S9, màn hình Always-On Retina, tính năng Double Tap và theo dõi sức khỏe toàn diện.', 'apple-watch-s9.jpg', 1, true);

-- Insert sample products for Fashion category
INSERT INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(9, 'Áo sơ mi nam công sở Premium', 599000, 'Áo sơ mi nam chất liệu cotton cao cấp, form slim fit, phù hợp cho môi trường công sở và các sự kiện trang trọng. Có nhiều màu sắc lựa chọn.', 'ao-so-mi-nam.jpg', 2, true),
(10, 'Váy đầm nữ dự tiệc sang trọng', 1299000, 'Váy đầm nữ thiết kế thanh lịch với chất liệu lụa cao cấp, phù hợp cho các buổi tiệc và sự kiện quan trọng. Thiết kế tôn dáng và thời trang.', 'vay-dam-nu.jpg', 2, true),
(11, 'Quần jeans nam Slim Fit', 899000, 'Quần jeans nam chất liệu denim cao cấp, form slim fit tôn dáng, có độ co giãn thoải mái. Thiết kế hiện đại phù hợp mọi lứa tuổi.', 'quan-jeans-nam.jpg', 2, true),
(12, 'Áo khoác nữ mùa đông', 1599000, 'Áo khoác nữ chất liệu dạ cao cấp, thiết kế ấm áp và thời trang. Phù hợp cho mùa đông với nhiều màu sắc lựa chọn.', 'ao-khoac-nu.jpg', 2, true);

-- Insert sample products for Home & Kitchen category
INSERT INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(13, 'Nồi cơm điện Panasonic 1.8L', 2299000, 'Nồi cơm điện cao cấp với công nghệ nấu áp suất, lòng nồi chống dính Diamond, dung tích 1.8L phù hợp cho gia đình 4-6 người.', 'noi-com-dien.jpg', 3, true),
(14, 'Máy xay sinh tố Philips HR3573', 1899000, 'Máy xay sinh tố đa năng với công suất 1000W, cối xay inox cao cấp, 6 tốc độ xay và chức năng tự động. Bảo hành 2 năm.', 'may-xay-sinh-to.jpg', 3, true),
(15, 'Lò vi sóng Sharp 25L', 3299000, 'Lò vi sóng điện tử với dung tích 25L, công suất 900W, có chức năng nướng và hâm nóng thông minh. Thiết kế hiện đại, tiết kiệm điện.', 'lo-vi-song.jpg', 3, true),
(16, 'Máy lọc nước RO Kangaroo', 4599000, 'Máy lọc nước RO 9 lõi lọc, công nghệ lọc tiên tiến loại bỏ 99.9% vi khuẩn và tạp chất. Có tủ đựng inox cao cấp và vòi nóng lạnh.', 'may-loc-nuoc.jpg', 3, true);

-- Insert sample products for Books & Stationery category
INSERT INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(17, 'Sách lập trình Java từ cơ bản đến nâng cao', 299000, 'Sách hướng dẫn lập trình Java toàn diện từ cơ bản đến nâng cao, bao gồm Spring Boot, JPA và các framework hiện đại. Phiên bản cập nhật 2024.', 'sach-java.jpg', 4, true),
(18, 'Bộ bút bi cao cấp Parker', 899000, 'Bộ bút bi Parker Jotter Premium với thiết kế sang trọng, mực viết mượt mà và bền bỉ. Phù hợp làm quà tặng hoặc sử dụng cá nhân.', 'but-bi-parker.jpg', 4, true),
(19, 'Máy tính Casio FX-580VN X', 599000, 'Máy tính khoa học Casio với 552 chức năng, màn hình hiển thị tự nhiên, hỗ trợ tính toán phức tạp cho học sinh và sinh viên.', 'may-tinh-casio.jpg', 4, true),
(20, 'Sổ tay da cao cấp A5', 399000, 'Sổ tay bìa da thật cao cấp khổ A5, giấy in chất lượng cao, có dây đánh dấu và túi đựng danh thiếp. Thiết kế sang trọng và bền đẹp.', 'so-tay-da.jpg', 4, true);

-- Insert sample products for Sports & Travel category
INSERT INTO products (id, name, price, description, image, category_id, is_active) VALUES 
(21, 'Giày chạy bộ Nike Air Zoom', 2599000, 'Giày chạy bộ Nike với công nghệ Air Zoom, đế giữa React foam tạo cảm giác êm ái và phản hồi tốt. Thiết kế thoáng khí và bền bỉ.', 'giay-nike.jpg', 5, true),
(22, 'Ba lô du lịch Samsonite 40L', 1899000, 'Ba lô du lịch cao cấp với dung tích 40L, chất liệu chống nước, nhiều ngăn tiện ích và dây đeo ergonomic thoải mái.', 'balo-samsonite.jpg', 5, true),
(23, 'Xe đạp thể thao Giant', 8999000, 'Xe đạp thể thao Giant với khung nhôm nhẹ, hệ thống sang số Shimano 21 tốc độ, phanh đĩa hydraulic và thiết kế aerodynamic.', 'xe-dap-giant.jpg', 5, true);

-- Create sample carts for users
INSERT INTO carts (id, user_id) VALUES 
(1, 2),
(2, 3),
(3, 4);

-- Insert sample cart items
INSERT INTO cart_items (id, cart_id, product_id, quantity) VALUES 
(1, 1, 1, 1),  -- user1 has iPhone in cart
(2, 1, 5, 1),  -- user1 has Sony headphones in cart
(3, 2, 3, 1),  -- user2 has MacBook in cart
(4, 2, 9, 2),  -- user2 has 2 shirts in cart
(5, 3, 13, 1), -- user3 has rice cooker in cart
(6, 3, 17, 3); -- user3 has 3 Java books in cart

-- Insert sample orders
INSERT INTO orders (id, user_id, order_date, total_amount, status, address) VALUES 
(1, 2, '2024-04-10 10:30:00', 35980000, 'DELIVERED', '123 Nguyễn Văn Cừ, Quận 5, TP.HCM'),
(2, 3, '2024-04-12 14:15:00', 28589000, 'SHIPPING', '456 Lê Văn Việt, Quận 9, TP.HCM'),
(3, 4, '2024-04-13 09:45:00', 2898000, 'CONFIRMED', '789 Võ Văn Tần, Quận 3, TP.HCM'),
(4, 2, '2024-04-14 16:20:00', 6590000, 'PENDING', '123 Nguyễn Văn Cừ, Quận 5, TP.HCM'),
(5, 3, '2024-04-15 11:00:00', 1899000, 'CONFIRMED', '456 Lê Văn Việt, Quận 9, TP.HCM');

-- Insert sample order details
INSERT INTO order_details (id, order_id, product_id, quantity, price) VALUES 
-- Order 1 details (user2 - delivered)
(1, 1, 1, 1, 29990000),  -- iPhone 15 Pro Max
(2, 1, 5, 1, 7990000),   -- Sony headphones
-- Order 2 details (user3 - shipping)
(3, 2, 3, 1, 27990000),  -- MacBook Air M3
(4, 2, 9, 1, 599000),    -- Shirt
-- Order 3 details (user4 - confirmed)
(5, 3, 13, 1, 2299000),  -- Rice cooker
(6, 3, 17, 2, 299000),   -- Java books (2 copies)
-- Order 4 details (user2 - pending)
(7, 4, 7, 1, 5990000),   -- AirPods Pro
(8, 4, 18, 1, 599000),   -- Parker pen
-- Order 5 details (user3 - confirmed)
(9, 5, 14, 1, 1899000);  -- Blender

-- Reset AUTO_INCREMENT values to ensure proper sequencing
ALTER TABLE categories AUTO_INCREMENT = 9;
ALTER TABLE users AUTO_INCREMENT = 6;
ALTER TABLE products AUTO_INCREMENT = 24;
ALTER TABLE carts AUTO_INCREMENT = 4;
ALTER TABLE cart_items AUTO_INCREMENT = 7;
ALTER TABLE orders AUTO_INCREMENT = 6;
ALTER TABLE order_details AUTO_INCREMENT = 10;