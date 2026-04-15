package poly.edu.asm_be;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void testPasswordEncoding() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
        System.out.println("Matches: " + encoder.matches(rawPassword, encodedPassword));
        
        // Generate new hash
        String newHash = encoder.encode(rawPassword);
        System.out.println("\nNew hash generated: " + newHash);
        System.out.println("New hash matches: " + encoder.matches(rawPassword, newHash));
    }
}
