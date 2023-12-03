package com.haithemsboui.meetingbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @ManyToMany
    @JoinTable(
            name = "meeting_details",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> organizer = new HashSet<>();

    private String title;

    @Column( length = 1000)
    private String description;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "max_attendees")
    private int maxAttendees;

    private MeetingStatus status;


//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private Set<Booking> bookings = new HashSet<>();


}
