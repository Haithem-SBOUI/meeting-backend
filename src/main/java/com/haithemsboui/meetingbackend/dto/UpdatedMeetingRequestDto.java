package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedMeetingRequestDto {

    @NotNull
    private String title;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

    private int maxAttendees;

    private MeetingStatus status;
}
