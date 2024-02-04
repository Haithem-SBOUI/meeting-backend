package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.UserRole;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.haithemsboui.meetingbackend.model.User}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String firstname;
    String lastname;
    String password;
    String email;
    String imageUrl;
    UserRole role;
    String status;
}