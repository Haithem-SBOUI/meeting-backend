package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.core.exception.type.InternalServerErrorException;
import com.haithemsboui.meetingbackend.core.exception.type.NotFoundException;
import com.haithemsboui.meetingbackend.dto.MeetingDto;
import com.haithemsboui.meetingbackend.dto.modelMapper.MeetingMapper;
import com.haithemsboui.meetingbackend.dto.modelMapper.UserMapper;
import com.haithemsboui.meetingbackend.model.Meeting;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import com.haithemsboui.meetingbackend.repository.MeetingRepository;
import com.haithemsboui.meetingbackend.repository.UserRepository;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final UserMapper userMapper;

    public MeetingDto createMeeting(MeetingDto newMeeting) {

        if (userRepository.existsById(newMeeting.getOrganizer().getId())) {
            newMeeting.setRoomId(generateRoomId());
            Meeting meeting = meetingMapper.toEntity(newMeeting);
            Meeting createdMeeting = meetingRepository.save(meeting);
            MeetingDto meetingDto = meetingMapper.toDto(createdMeeting);
//            todo: add create responseDto methode to return result to frontend and edit frontend to show them
            return meetingDto;
        } else {
            throw new NotFoundException("Organizer not Found");
        }
    }

    public String generateRoomId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 3) + "-" + uuid.substring(9, 12) + "-" + uuid.substring(14, 17);
    }


    public List<MeetingDto> getAllMeeting() {
        List<Meeting> allMeetings = meetingRepository.findAll();
        if (!allMeetings.isEmpty()) {
            return meetingMapper.toDtoList(allMeetings);
        } else {
            return Collections.emptyList();
        }
    }


    public List<MeetingDto> getMeetingByOrganizerEmail(String email) {
        List<Meeting> fetchedMeetings = meetingRepository.findByOrganizerEmail(email);
        if (!fetchedMeetings.isEmpty()) {
            return meetingMapper.toDtoList(fetchedMeetings);
        } else {
            return Collections.emptyList();
        }
    }

    public List<MeetingDto> getMeetingByDate(LocalDate date) {
        LocalDateTime dateTimeMin = date.atTime(LocalTime.MIN);
        LocalDateTime dateTimeMax = date.atTime(LocalTime.MAX);

        List<Meeting> fetchedMeetings = meetingRepository.findByDateTimeBetween(dateTimeMin, dateTimeMax);
        if (!fetchedMeetings.isEmpty()) {
            return meetingMapper.toDtoList(fetchedMeetings);
        } else {
            return Collections.emptyList();
        }
    }

    public List<MeetingDto> getMeetingByStatus(MeetingStatus status) {
        List<Meeting> fetchedMeetings = meetingRepository.findByStatus(status);
        if (!fetchedMeetings.isEmpty()) {
            return meetingMapper.toDtoList(fetchedMeetings);
        } else {
            return Collections.emptyList();
        }
    }


    public MeetingDto getMeetingByRoomId(String roomId) {
        Optional<Meeting> fetchedMeeting = meetingRepository.findByRoomId(roomId);
        if (fetchedMeeting.isPresent()) {
            return meetingMapper.toDto(fetchedMeeting.get());
        } else {
            throw new NotFoundException("Meeting with room id : " + roomId + " not found!");
        }
    }


    public String updateMeetingStatus(Long id, MeetingStatus newStatus) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Meeting not found"));
        meeting.setStatus(newStatus);
        meetingRepository.save(meeting);
        return "Status changed to " + newStatus + " successfully";
    }


    public String updateMeeting(Long id, MeetingDto updatedMeetingDto) {
        Meeting existingMeeting = meetingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Meeting not found"));
        Meeting meeting = meetingMapper.toEntity(updatedMeetingDto);
        meetingRepository.save(meeting);
        return "Meeting updated successfully";
    }

    public void deleteMeetingById(Long id) {
        meetingRepository.deleteById(id);
        if (meetingRepository.existsById(id)) {
            throw new InternalServerErrorException("problem while delete Meeting with id : " + id);
        }
    }
}
