package com.haithemsboui.meetingbackend.controller;


import com.haithemsboui.meetingbackend.dto.MeetingDto;
import com.haithemsboui.meetingbackend.dto.StringToJsonDto;
import com.haithemsboui.meetingbackend.dto.UpdatedMeetingRequestDto;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import com.haithemsboui.meetingbackend.service.MeetingService;
import com.sun.jdi.InternalException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<?> createMeeting(@RequestBody @Valid MeetingDto createMeetingDto) {
        MeetingDto response = meetingService.createMeeting(createMeetingDto);
        return ResponseEntity.ok().body(response);
    }

    //    ### GET ###
//    get all


    @GetMapping("/get-public-meeting")
    public ResponseEntity<?> getPublicMeetings() {
        List<MeetingDto> publicMeetings = meetingService.getPublicMeetings();
        if (!publicMeetings.isEmpty()) {
            return ResponseEntity.ok(publicMeetings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/get-meeting-by-room-id/{roomId}")
    public ResponseEntity<?> getMeetingByRoomId(@PathVariable String roomId) {
        MeetingDto fetchedMeeting = meetingService.getMeetingByRoomId(roomId);
        return ResponseEntity.ok(fetchedMeeting);
    }


    //    get all by organizer
    @GetMapping("/get-meeting-by-organizer-email")
    public ResponseEntity<?> getMeetingByOrganizerEmail(@RequestParam String email) {
        List<MeetingDto> fetchedMeetings = meetingService.getMeetingByOrganizerEmail(email);
        if (!fetchedMeetings.isEmpty()) {
            return ResponseEntity.ok(fetchedMeetings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //    get all by date
    @GetMapping("/get-meeting-by-date/{date}")
    public ResponseEntity<?> getMeetingByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MeetingDto> fetchedMeetings = meetingService.getMeetingByDate(date);
        if (!fetchedMeetings.isEmpty()) {
            return ResponseEntity.ok(fetchedMeetings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //    get all by status

    @GetMapping("/get-meeting-by-status")
    public ResponseEntity<?> getMeetingByStatus(@RequestParam MeetingStatus status) {
        List<MeetingDto> fetchedMeetings = meetingService.getMeetingByStatus(status);
        if (!fetchedMeetings.isEmpty()) {
            return ResponseEntity.ok(fetchedMeetings);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    //    ### update ###

    //    update meeting details
    @PutMapping("/update-meeting/{id}/")
    public ResponseEntity<?> updateMeeting(@PathVariable Long id, @RequestBody MeetingDto updatedMeetingDto) {
        return ResponseEntity.ok().body(
                StringToJsonDto.builder()
                        .message(meetingService.updateMeeting(id, updatedMeetingDto))
                        .build());

        }




    @PutMapping("/update-meeting-status/{id}/{newStatus}")
    public ResponseEntity<?> updateMeetingStatus(@PathVariable Long id, @PathVariable MeetingStatus newStatus) {
        return ResponseEntity.ok().body(
                StringToJsonDto.builder()
                        .message(meetingService.updateMeetingStatus(id, newStatus))
                        .build());
    }




//    ### DELETE ###
//    delete by organizer id


    //    delete by meeting id
    @DeleteMapping("/delete-meeting-by-id/{id}")
    public ResponseEntity<?> deleteMeetingById(@PathVariable Long id) {
            meetingService.deleteMeetingById(id);
            return ResponseEntity.noContent().build();

    }

}
