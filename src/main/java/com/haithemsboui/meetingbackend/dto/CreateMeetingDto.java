package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMeetingDto {
    private Long organizer_id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private int maxAttendees;
    private MeetingStatus status;

}
