package com.haithemsboui.meetingbackend.controller;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.dto.StringToJsonDto;
import com.haithemsboui.meetingbackend.dto.UpdatePasswordDto;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.model.UserRole;
import com.haithemsboui.meetingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequestDto newUser) {
//        return userService.register(newUser);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequest) {
//        return userService.login(authRequest);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody String email) {
//        return userService.logout(email);
//    }
//
//    @GetMapping("/all-user")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/all-user-roles")
//    public UserRole[] getAllUserRoles() {
//        return UserRole.values();
//    }
//
//
//    @GetMapping("/show-user-by-id/{id}")
//    public Optional<User> getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/show-user-by-email/{email}")
//    public Optional<User> getUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email);
//    }
//
//    @PutMapping("/update-user/{id}")
//    public User deleteUserById(@RequestBody RegisterRequestDto updatedUser, @PathVariable Long id) {
//        return userService.updateUser(updatedUser, id);
//    }
//
//    @PutMapping("/update-password/{id}")
//    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto updatePasswordDto) {
//        try {
//            userService.updatePassword(id, updatePasswordDto);
//            return ResponseEntity.ok(
//                    StringToJsonDto
//                            .builder()
//                            .message("password changed successfully")
//                            .build());
//        } catch (RuntimeException e) {
//            if (e.getMessage().equals("wrong password")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(StringToJsonDto
//                                .builder()
//                                .message(e.getMessage())
//                                .build());
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(StringToJsonDto
//                                .builder()
//                                .message("User Not Found!")
//                                .build());
//
//            }
//
//        }
//    }
//
//
//    @DeleteMapping("/delete-user/{id}")
//    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
//        return userService.deleteUserById(id);
//    }


}
