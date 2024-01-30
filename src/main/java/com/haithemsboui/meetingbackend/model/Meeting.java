package com.haithemsboui.meetingbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id ;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "room_id", unique = true)
    private String roomId;

    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "max_attendees")
    private int maxAttendees;

    private MeetingStatus status;


//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private Set<Booking> bookings = new HashSet<>();


}
