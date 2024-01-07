package com.haithemsboui.meetingbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDto {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String imageUrl;
    private String role;
}
