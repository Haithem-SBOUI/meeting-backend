package com.haithemsboui.meetingbackend.repository;

import com.haithemsboui.meetingbackend.model.Meeting;
import com.haithemsboui.meetingbackend.model.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
    @Query("select m from Meeting m join m.organizer o where o.email = 'string@email1.com'")
    List<Meeting> findByOrganizerEmail(String email);



    @Query("SELECT m FROM Meeting m WHERE m.dateTime BETWEEN :dateTimeMin AND :dateTimeMax")
    List<Meeting> findByDateTimeBetween(
            @Param("dateTimeMin") LocalDateTime dateTimeMin,
            @Param("dateTimeMax") LocalDateTime dateTimeMax
    );


    List<Meeting> findByStatus(MeetingStatus status);

    Optional<Meeting> findByRoomId(String roomId);


    @Override
    Optional<Meeting> findById(UUID uuid);

}
