package com.haithemsboui.meetingbackend.controller;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.dto.StringToJsonDto;
import com.haithemsboui.meetingbackend.dto.UpdatePasswordDto;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.model.UserRole;
import com.haithemsboui.meetingbackend.service.AuthService;
import com.haithemsboui.meetingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto newUser) {
        return ResponseEntity.ok(authService.register(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

}
