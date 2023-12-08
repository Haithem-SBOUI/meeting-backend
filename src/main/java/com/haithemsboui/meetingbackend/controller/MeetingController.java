package com.haithemsboui.meetingbackend.controller;


import com.haithemsboui.meetingbackend.dto.CreateMeetingDto;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import com.haithemsboui.meetingbackend.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meeting")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;


    //    ### POST ###
//    ############################################################################
//    TODO : update creation to add generate meet code to be like google meet
//    ############################################################################

    //    create =>
    @PostMapping("/create")
    public ResponseEntity<String> createMeeting(@RequestBody CreateMeetingDto createMeetingDto) {
        return meetingService.createMeeting(createMeetingDto);
    }

    //    ### GET ###
//    get all
    @GetMapping("/get-all-meeting")
    public ResponseEntity<?> getAllMeeting() {
        return meetingService.getAllMeeting();
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
//    change status (CANCELED)
    @PutMapping("/update-meeting-status/{id}/{status}")
    public ResponseEntity<String> updateMeetingStatus(@PathVariable UUID id, @PathVariable MeetingStatus status){
        return meetingService.updateMeetingStatus(id, status);
    }

//    ### DELETE ###
//    delete by organizer id


//    delete by meeting id



}
