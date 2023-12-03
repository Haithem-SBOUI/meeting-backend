package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.dto.CreateMeetingDto;
import com.haithemsboui.meetingbackend.dto.RegisterRequestDto;
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

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    public ResponseEntity<String> createMeeting(CreateMeetingDto newMeeting) {
        try {
            Meeting meeting = convertDtoToMeetingEntity(newMeeting);
            meetingRepository.save(meeting);
            return ResponseEntity.status(HttpStatus.CREATED).body("Meeting created successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Meeting creation failed due to a database constraint");

        }
    }

    private Meeting convertDtoToMeetingEntity(CreateMeetingDto newMeeting) {
        Meeting meeting = new Meeting();
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

    public ResponseEntity<?> getAllMeeting() {
        return ResponseEntity.ok(meetingRepository.findAll());
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
            return ResponseEntity.ok("Status changed to " + status +" successfully");
        } else {
            return ResponseEntity.ok("meeting with id = " + id + " not found");

        }
    }
}
