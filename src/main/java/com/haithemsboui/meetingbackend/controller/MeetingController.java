package com.haithemsboui.meetingbackend.controller;


import com.haithemsboui.meetingbackend.dto.CreateMeetingRequestDto;
import com.haithemsboui.meetingbackend.dto.StringToJsonDto;
import com.haithemsboui.meetingbackend.dto.UpdatedMeetingRequestDto;
import com.haithemsboui.meetingbackend.model.Meeting;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import com.haithemsboui.meetingbackend.service.MeetingService;
import com.sun.jdi.InternalException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meeting")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;


    //    ### POST ###


    //    create =>
    @PostMapping("/create")
    public ResponseEntity<?> createMeeting(@RequestBody @Valid CreateMeetingRequestDto createMeetingDto) {
        return meetingService.createMeeting(createMeetingDto);
    }

    //    ### GET ###
//    get all
    @GetMapping("/get-all-meeting")
    public List<Meeting> getAllMeeting() {
        return meetingService.getAllMeeting();
    }

    @GetMapping("/get-meeting-by-room-id/{roomId}")
    public Optional<Meeting> getMeetingByRoomId(@PathVariable String roomId) {
        return meetingService.getMeetingByRoomId(roomId);
    }


    //    get all by organizer
    @GetMapping("/get-meeting-by-organizer-email")
    public ResponseEntity<?> getMeetingByOrganizerEmail(@RequestParam String email) {
        System.out.println("hadha ml controller" + email);
        return meetingService.getMeetingByOrganizerEmail(email);
    }

    //    get all by date
    @GetMapping("/get-meeting-by-date/{date}")
    public ResponseEntity<?> getMeetingByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return meetingService.getMeetingByDate(date);
    }

    //    get all by status

    @GetMapping("/get-meeting-by-status")
    public ResponseEntity<?> getMeetingByStatus(@RequestParam MeetingStatus status) {
        return meetingService.getMeetingByStatus(status);
    }


    //    ### update ###

    //    update meeting details
    @PutMapping("/update-meeting/{id}/")
    public ResponseEntity<?> updateMeeting(@PathVariable UUID id, @RequestBody UpdatedMeetingRequestDto updatedMeetingRequestDto) {
        try {
            return ResponseEntity.ok(
                    StringToJsonDto.builder()
                            .message(meetingService.updateMeeting(id, updatedMeetingRequestDto))
                            .build());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    //    change status (CANCELED)
    @PutMapping("/update-meeting-status/{id}/{status}")
    public ResponseEntity<String> updateMeetingStatus(@PathVariable UUID id, @PathVariable MeetingStatus status) {
        return meetingService.updateMeetingStatus(id, status);
    }

//    ### DELETE ###
//    delete by organizer id


    //    delete by meeting id
    @DeleteMapping("/delete-meeting-by-id/{id}")
    public ResponseEntity<?> deleteMeetingById(@PathVariable UUID id) {
        try {
            meetingService.deleteMeetingById(id);
            return ResponseEntity.noContent().build();
        }catch( InternalException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

}
