package com.restfulApi.controller;

import com.restfulApi.entity.User;
import com.restfulApi.payload.JWTAuthResponse;
import com.restfulApi.payload.LoginDto;
import com.restfulApi.payload.SignUpDto;
import com.restfulApi.repository.UserRepository;
import com.restfulApi.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // User Login Endpoint
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token
            String token = tokenProvider.generateToken(authentication);

            // Return the token in a structured response
            return ResponseEntity.ok(new JWTAuthResponse(token));

        } catch (Exception e) {
            // If authentication fails, return an error response
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.UNAUTHORIZED.value(),
                    "message", "Invalid username or password",
                    "timestamp", new Date()
            ), HttpStatus.UNAUTHORIZED);
        }
    }

    // User Registration Endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        // Check if username already exists
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "Username is already taken!",
                    "timestamp", new Date()
            ), HttpStatus.BAD_REQUEST);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "Email is already taken!",
                    "timestamp", new Date()
            ), HttpStatus.BAD_REQUEST);
        }

        // Create new user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // Save the user in the database
        userRepository.save(user);

        // Return success response
        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "User registered successfully",
                "timestamp", new Date()
        ), HttpStatus.OK);
    }
}
