package com.haithemsboui.meetingbackend.controller;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
//@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    @GetMapping("/hello")
    public ResponseEntity<?> register() {
        return ResponseEntity.ok("hello from protected");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("hello 2 from protected");
    }



}
