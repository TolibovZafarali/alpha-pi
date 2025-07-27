package com.example.backend.controller;


import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserSignUpDTO;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpDTO signUpDTO) {

        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        User newUser = new User(signUpDTO.getEmail(), hashedPassword, signUpDTO.getRole());
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.ok("Login successful.");
            } else {
                return ResponseEntity.status(401).body("Incorrect password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
