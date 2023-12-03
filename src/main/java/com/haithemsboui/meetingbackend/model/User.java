package com.haithemsboui.meetingbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
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
    @Column(name = "image_url")
    private String imageUrl;


    @Column(length = 50, nullable = false)
    private String role;

    private String status;

}
