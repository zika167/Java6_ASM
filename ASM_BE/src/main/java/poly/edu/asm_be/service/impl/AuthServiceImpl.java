package poly.edu.asm_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poly.edu.asm_be.dto.AuthResponse;
import poly.edu.asm_be.dto.ChangePasswordRequest;
import poly.edu.asm_be.dto.RegisterRequest;
import poly.edu.asm_be.dto.UserDTO;
import poly.edu.asm_be.entity.User;
import poly.edu.asm_be.repository.UserRepository;
import poly.edu.asm_be.service.AuthService;
import poly.edu.asm_be.service.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Validation
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullname(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(User.Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        // Create response
        AuthResponse response = new AuthResponse();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setFullname(savedUser.getFullname());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setRedirectUrl("/login");

        return response;
    }

    @Override
    public AuthResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        AuthResponse response = new AuthResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO) {
        User currentUser = getCurrentAuthenticatedUser();
        
        currentUser.setFullname(userDTO.getFullname());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setPhone(userDTO.getPhone());

        User updatedUser = userRepository.save(currentUser);

        UserDTO response = new UserDTO();
        response.setId(updatedUser.getId());
        response.setUsername(updatedUser.getUsername());
        response.setFullname(updatedUser.getFullname());
        response.setEmail(updatedUser.getEmail());
        response.setPhone(updatedUser.getPhone());
        response.setRole(updatedUser.getRole());

        return response;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User currentUser = getCurrentAuthenticatedUser();

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Validate new password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        // Update password
        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(currentUser);
    }

    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        return userRepository.findById(principal.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}