package com.haithemsboui.meetingbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
