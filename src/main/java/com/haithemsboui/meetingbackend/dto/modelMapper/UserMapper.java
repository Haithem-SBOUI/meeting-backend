package com.haithemsboui.meetingbackend.dto.modelMapper;

import com.haithemsboui.meetingbackend.dto.UserDto;
import com.haithemsboui.meetingbackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;
    public UserDto toDTO(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
