package com.haithemsboui.meetingbackend.repository;

import com.haithemsboui.meetingbackend.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
}
