package com.simple.crud.api.auth.service;

import com.simple.crud.api.auth.dto.LoginRequest;
import com.simple.crud.api.auth.dto.LoginResponse;
import com.simple.crud.api.auth.dto.RegisterRequest;
import com.simple.crud.api.jwt.JwtService;
import com.simple.crud.api.user.entity.User;
import com.simple.crud.api.user.enums.Role;
import com.simple.crud.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new LoginResponse(jwtService.generateToken(user));
    }

    public LoginResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return new LoginResponse(jwtService.generateToken(user));
    }
}
