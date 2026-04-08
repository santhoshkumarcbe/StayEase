package com.takehome.stayease.service;

import java.util.List;
import com.takehome.stayease.dto.request.LoginRequest;
import com.takehome.stayease.dto.request.RegisterRequest;
import com.takehome.stayease.dto.response.AuthResponse;
import com.takehome.stayease.entity.Role;
import com.takehome.stayease.entity.User;
import com.takehome.stayease.exception.ResourceNotFoundException;
import com.takehome.stayease.repository.UserRepository;
import com.takehome.stayease.security.JwtService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) throws BadRequestException {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        String role = request.getRole();

        if (role == null || role.isBlank()) {
            user.setRole(Role.ROLE_USER);
        } else {
            switch (role.toUpperCase()) {
                case "ADMIN" -> user.setRole(Role.ROLE_ADMIN);
                case "HOTEL_MANAGER" -> user.setRole(Role.ROLE_HOTEL_MANAGER);
                case "USER" -> user.setRole(Role.ROLE_USER);
                default -> throw new BadRequestException("Invalid role");
            }
        }        

        userRepository.save(user);

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole().name()))
                )
        );

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) throws BadRequestException {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole().name()))
                )
        );

        return new AuthResponse(token);
    }
}
