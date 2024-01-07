package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UpdatedMeetingRequestDto {

    @NotNull
    private String title;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

    private int maxAttendees;

    private MeetingStatus status;
}
