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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "meeting_id")
    private UUID meetingId = UUID.randomUUID();

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "meeting_details",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> organizer = new HashSet<>();

    @Column( name = "room_id", unique = true)
    private String roomId;


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
