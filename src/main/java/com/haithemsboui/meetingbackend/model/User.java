package com.haithemsboui.meetingbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToMany(mappedBy = "organizer")
    @JsonBackReference
    private Set<Meeting> meetings = new HashSet<>();


    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    //    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'https://www.freeiconspng.com/uploads/profile-icon-28.png'")
    @Column(name = "image_url", columnDefinition = "VARCHAR(255) DEFAULT 'https://www.freeiconspng.com/uploads/profile-icon-28.png'")
    private String imageUrl;


    @Column(length = 50, nullable = false)
    private UserRole role;

    private String status;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;





}
