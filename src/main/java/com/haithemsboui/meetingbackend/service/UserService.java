package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.AuthResponseDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.dto.UpdatePasswordDto;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.model.UserRole;
import com.haithemsboui.meetingbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public ResponseEntity<?> login(AuthRequestDto authRequest) {
        Optional<User> user = userRepository.findByEmail(authRequest.getEmail());
        if (user.isPresent()) {
            if (authRequest.getPassword().equals(user.get().getPassword())) {
                Long id = user.get().getId();
                String email = user.get().getEmail();
                String username = user.get().getUsername();
                user.get().setStatus("online");
                userRepository.save(user.get());
                return ResponseEntity.ok(AuthResponseDto.builder()
                        .id(id)
                        .email(email)
                        .username(username)
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }


    public ResponseEntity<String> logout(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setStatus("online");
            userRepository.save(user.get());
            return ResponseEntity.ok("logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("logged out successfully");
        }

    }

    public ResponseEntity<?> register(RegisterRequestDto newUser) {
        try {
            User ConvertedUser = convertDtoToUserEntity(newUser);
            userRepository.save(ConvertedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (DataIntegrityViolationException e) {
            String username = newUser.getUsername();
            String email = newUser.getEmail();
            String errorMessage = e.getCause().getMessage();
            if (errorMessage.contains(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
            } else if (errorMessage.contains(email)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            } else {
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed due to a database constraint");
            }
        }
    }

    private User convertDtoToUserEntity(RegisterRequestDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        user.setPassword(newUser.getPassword());
        user.setImageUrl(newUser.getImageUrl());
        user.setRole(UserRole.valueOf(newUser.getRole()));
        user.setStatus("offline");
        return user;
    }

    public Optional<User> getUserById(Long userId) {
        return Optional.of(userRepository.findById(userId).get());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String userName) {
        return Optional.ofNullable(userRepository.findByUsername(userName));
    }


    public User updateUser(RegisterRequestDto updatedUser, Long id) {
        User ConvertedUser = convertDtoToUserEntity(updatedUser);
        User existingUser = userRepository.findById(id).get();
        existingUser.setUsername(ConvertedUser.getUsername());
        existingUser.setFirstname(ConvertedUser.getFirstname());
        existingUser.setLastname(ConvertedUser.getLastname());
        existingUser.setEmail(ConvertedUser.getEmail());
        existingUser.setPassword(ConvertedUser.getPassword());
        existingUser.setRole(ConvertedUser.getRole());
        return userRepository.save(existingUser);
    }


    @Transactional
    public ResponseEntity<String> deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else if (!userRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found, id = " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User not found, id = " + id);
        }
    }

    public void updatePassword(Long id, UpdatePasswordDto updatePasswordDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(updatePasswordDto.getOldPassword())) {
                user.get().setPassword(updatePasswordDto.getNewPassword());
                userRepository.save(user.get());
            }else {
                throw new RuntimeException("wrong password");
            }
        }else {
            throw new RuntimeException("wrong user id");
        }
    }
}
