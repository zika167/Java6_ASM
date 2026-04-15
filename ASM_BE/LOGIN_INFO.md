# Thông tin đăng nhập

## Tài khoản mẫu

Sau khi đã sửa lại mật khẩu trong `data.sql` với BCrypt hash, bạn cần **RESTART** ứng dụng để database được tạo lại với mật khẩu đã mã hóa.

### Tài khoản Admin:
- **Username:** admin
- **Password:** password123
- **Role:** ROLE_ADMIN

### Tài khoản Manager:
- **Username:** manager  
- **Password:** password123
- **Role:** ROLE_ADMIN

### Tài khoản User:
- **Username:** user1
- **Password:** password123
- **Role:** ROLE_USER

- **Username:** user2
- **Password:** password123
- **Role:** ROLE_USER

- **Username:** user3
- **Password:** password123
- **Role:** ROLE_USER

## Cách restart ứng dụng:

1. Dừng ứng dụng hiện tại (Ctrl+C trong terminal)
2. Chạy lại: `./mvnw spring-boot:run` (Linux/Mac) hoặc `mvnw.cmd spring-boot:run` (Windows)
3. Database sẽ tự động được drop và tạo lại với mật khẩu đã mã hóa
4. Truy cập http://localhost:8080/login và đăng nhập với tài khoản trên

## Lưu ý:
- Mật khẩu trong database đã được mã hóa bằng BCrypt
- BCrypt hash đã được verify của 'password123': `$2a$10$p1OgKNonuhXvqtiS0mfxYe0ThTcjvzDQuBzDqVria6qKp/44q8PoK`
- Khi đăng ký user mới qua form, mật khẩu sẽ tự động được mã hóa

## Nếu vẫn không đăng nhập được:
1. Kiểm tra console log xem có thông báo "Loading user: admin" không
2. Kiểm tra password hash có bắt đầu bằng "$2a$10$p1O" không
3. Đảm bảo đã restart ứng dụng sau khi sửa data.sql
