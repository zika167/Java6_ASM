package poly.edu.asm_be.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import poly.edu.asm_be.dto.*;
import poly.edu.asm_be.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create session
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // Get user info
        AuthResponse response = authService.getCurrentUser();
        response.setRedirectUrl(response.getRole() == poly.edu.asm_be.entity.User.Role.ROLE_ADMIN ? "/admin" : "/");

        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponse>> getCurrentUser() {
        AuthResponse response = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> updateProfile(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = authService.updateProfile(userDTO);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", updatedUser));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<ApiResponse<Boolean>> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && 
                                authentication.isAuthenticated() && 
                                !authentication.getName().equals("anonymousUser");
        
        return ResponseEntity.ok(ApiResponse.success(isAuthenticated));
    }
}