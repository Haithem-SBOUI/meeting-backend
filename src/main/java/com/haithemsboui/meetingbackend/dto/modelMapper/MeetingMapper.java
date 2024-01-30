package com.haithemsboui.meetingbackend.dto.modelMapper;

import com.haithemsboui.meetingbackend.dto.MeetingDto;
import com.haithemsboui.meetingbackend.dto.UserDto;
import com.haithemsboui.meetingbackend.model.Meeting;
import com.haithemsboui.meetingbackend.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class MeetingMapper {
    @Autowired
    private ModelMapper modelMapper;

    public MeetingDto toDto(Meeting meeting) {
        return modelMapper.map(meeting, MeetingDto.class);
    }

    public Meeting toEntity(MeetingDto meetingDto) {
        return modelMapper.map(meetingDto, Meeting.class);
    }

    public List<MeetingDto> toDtoList(List<Meeting> meetings) {
        return meetings.stream()
                .map(this::toDto)
                .collect(toList());
    }

}
