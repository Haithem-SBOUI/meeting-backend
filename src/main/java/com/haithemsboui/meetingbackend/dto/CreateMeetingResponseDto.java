package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class CreateMeetingResponseDto {
        private String roomId;
        private String title;
        private String description;
        private LocalDateTime dateTime;
        private int maxAttendees;
        private MeetingStatus status;
}
