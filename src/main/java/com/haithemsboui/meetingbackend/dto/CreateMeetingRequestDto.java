package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMeetingRequestDto {
    @NotNull
    private Long organizer_id;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

    private int maxAttendees;

    private MeetingStatus status;

}
