package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.dto.CreateMeetingRequestDto;
import com.haithemsboui.meetingbackend.dto.CreateMeetingResponseDto;
import com.haithemsboui.meetingbackend.dto.MeetingDetailsDto;
import com.haithemsboui.meetingbackend.model.Meeting;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import com.haithemsboui.meetingbackend.model.User;
import com.haithemsboui.meetingbackend.repository.MeetingRepository;
import com.haithemsboui.meetingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    public ResponseEntity<?> createMeeting(CreateMeetingRequestDto newMeeting) {
        try {
            Meeting meeting = convertDtoToMeetingEntity(newMeeting);
            meetingRepository.save(meeting);
//            todo: add create responseDto methode to return result to frontend and edit frontend to show them
            return ResponseEntity.ok().body(CreateMeetingResponseDto.builder()
                    .roomId(meeting.getRoomId())
                    .title(meeting.getTitle())
                    .maxAttendees(meeting.getMaxAttendees())
                    .dateTime(meeting.getDateTime())
                    .description(meeting.getDescription())
                    .status(meeting.getStatus())
                    .build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Meeting creation failed due to a database constraint");

        }
    }

    private Meeting convertDtoToMeetingEntity(CreateMeetingRequestDto newMeeting) {
        Meeting meeting = new Meeting();
        meeting.setRoomId(generateRoomId());
        meeting.setTitle(newMeeting.getTitle());
        meeting.setDescription(newMeeting.getDescription());
        meeting.setDateTime(newMeeting.getDateTime());
        meeting.setMaxAttendees(newMeeting.getMaxAttendees());
        meeting.setStatus(newMeeting.getStatus());

        User organizer = userRepository.findById(newMeeting.getOrganizer_id()).orElse(null);
        Set<User> organizers = new HashSet<>();
        organizers.add(organizer);
        meeting.setOrganizer(organizers);
        return meeting;
    }

    private String generateRoomId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 3) + "-" + uuid.substring(9, 12) + "-" + uuid.substring(14, 17);
    }

    // !in case i want to return only the organizer id and not the whole organizer object
//    public List<MeetingDetailsDto> getAllMeeting() {
//        List<Meeting> meetings = meetingRepository.findAll();
//
//        return meetings.stream()
//                .map(meeting -> MeetingDetailsDto.builder()
//                        .organizerId(getFirstOrganizerId(meeting.getOrganizer()))
//                        .roomId(meeting.getRoomId())
//                        .title(meeting.getTitle())
//                        .description(meeting.getDescription())
//                        .dateTime(meeting.getDateTime())
//                        .maxAttendees(meeting.getMaxAttendees())
//                        .status(meeting.getStatus())
//                        .build())
//                .collect(Collectors.toList());
//
//    }

    public List<Meeting> getAllMeeting() {
        return meetingRepository.findAll();

    }

    private Long getFirstOrganizerId(Set<User> organizers) {
        if (organizers != null && !organizers.isEmpty()) {
            return organizers.iterator().next().getUserId();
        }
        return null; // or handle the case when there are no organizers
    }

    public ResponseEntity<?> getMeetingByOrganizerEmail(String email) {
        List<Meeting> listMeeting = meetingRepository.findByOrganizerEmail(email);
        return ResponseEntity.ok(listMeeting);
    }

    public ResponseEntity<?> getMeetingByDate(LocalDate date) {
        LocalDateTime dateTimeMin = date.atTime(LocalTime.MIN);
        LocalDateTime dateTimeMax = date.atTime(LocalTime.MAX);

        List<Meeting> listMeeting = meetingRepository.findByDateTimeBetween(dateTimeMin, dateTimeMax);
        return ResponseEntity.ok(listMeeting);
    }

    public ResponseEntity<?> getMeetingByStatus(MeetingStatus status) {
        List<Meeting> listMeeting = meetingRepository.findByStatus(status);
        return ResponseEntity.ok(listMeeting);
    }

    public ResponseEntity<String> updateMeetingStatus(UUID id, MeetingStatus status) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if (meeting.isPresent()) {
            meeting.get().setStatus(status);
            meetingRepository.save(meeting.get());
            return ResponseEntity.ok("Status changed to " + status + " successfully");
        } else {
            return ResponseEntity.ok("meeting with id = " + id + " not found");

        }
    }

    public Optional<Meeting> getMeetingByRoomId(String roomId) {
        return meetingRepository.findByRoomId(roomId);
    }
}
