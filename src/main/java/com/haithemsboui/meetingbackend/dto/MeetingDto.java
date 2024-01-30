package com.haithemsboui.meetingbackend.dto;

import com.haithemsboui.meetingbackend.model.MeetingStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.haithemsboui.meetingbackend.model.Meeting}
 */
@Data

public class MeetingDto implements Serializable {
    Long id;
    UserDto organizer;
    String roomId;
    String title;
    String description;
    LocalDateTime dateTime;
    int maxAttendees;
    MeetingStatus status;

}