package com.haithemsboui.meetingbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {
    private Long id;
    private String email;
    private String username;
    private String token;
}
