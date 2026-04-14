package poly.edu.asm_be.service;

import poly.edu.asm_be.dto.AuthResponse;
import poly.edu.asm_be.dto.ChangePasswordRequest;
import poly.edu.asm_be.dto.RegisterRequest;
import poly.edu.asm_be.dto.UserDTO;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse getCurrentUser();
    UserDTO updateProfile(UserDTO userDTO);
    void changePassword(ChangePasswordRequest request);
}