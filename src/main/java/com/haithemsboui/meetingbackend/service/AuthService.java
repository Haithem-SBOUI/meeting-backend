package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.AuthResponseDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.model.UserRole;
import com.haithemsboui.meetingbackend.repository.UserRepository;
import com.haithemsboui.meetingbackend.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileUploadService fileUploadService;

    public AuthResponseDto register(RegisterRequestDto newUser) throws IOException {
        var user = User.builder()
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        if (newUser.getProfileImage() != null && !newUser.getProfileImage().isEmpty()) {
            String relativeImagePath = fileUploadService.uploadImage(newUser.getProfileImage());
            user.setProfilePicturePath(relativeImagePath);
        } else {
            user.setProfilePicturePath("uploads/avatar/default_user_avatar.png");
        }
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }


    public AuthResponseDto login(AuthRequestDto authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        System.out.println("ROle of logged user : " + user.getRole());
        System.out.println("token of logged user : " + jwtToken);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }


}


