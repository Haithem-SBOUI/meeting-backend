package com.haithemsboui.meetingbackend.controller;

import com.haithemsboui.meetingbackend.dto.AuthRequestDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto newUser){
        return userService.register(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequest){
        return userService.login(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String email){
        return userService.logout(email);
    }

    @GetMapping("/all-user")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/show-user-by-id/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/show-user-by-email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/update-user/{id}")
    public User deleteUserById(@RequestBody RegisterRequestDto updatedUser, @PathVariable Long id) {
        return userService.updateUser(updatedUser, id);
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }


}
