package com.scm.backend.controllers;

import com.scm.backend.dto.AuthRequest;
import com.scm.backend.dto.JwtResponse;
import com.scm.backend.dto.RefreshTokenRequest;
import com.scm.backend.entities.User;
import com.scm.backend.repositories.UserRepo;
import com.scm.backend.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;
    private final UserRepo userRepo;

    private AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Authentication logic here
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String accessToken = jwtService.generateToken(request.getEmail(), true);
        String refreshToken = jwtService.generateToken(request.getEmail(), false);
        User user = userRepo.findByEmail(request.getEmail()).get();
        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, user);
        return ResponseEntity.ok(jwtResponse);
    }

    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        if(jwtService.validateToken(request.refreshToken())){
            String usernameFromToken = jwtService.getUsernameFromToken(request.refreshToken());
            String accessToken = jwtService.generateToken(usernameFromToken, true);
            String refreshToken = jwtService.generateToken(request.refreshToken(), false);
            User user = userRepo.findByEmail(usernameFromToken).get();
            JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, user);
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }
}
