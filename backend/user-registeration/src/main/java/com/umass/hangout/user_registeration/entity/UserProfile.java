package com.umass.hangout.user_registeration.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long userId; // Primary key and foreign key

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String email;  // email field, non-editable in the profile page

    private String firstName;
    private String middleName;
    private String lastName;
    private String department;

    @Lob
    private String bio;
    private int graduationYear;
    private boolean firstTimeUpdate = true;


    // Default no-args constructor (required for JPA)
    public UserProfile() {}

    // All-args constructor
    public UserProfile(Long userId, String email, String firstName, String middleName,
                       String lastName, String department, String bio, Integer graduationYear) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.department = department;
        this.bio = bio;
        this.graduationYear = graduationYear;
    }

    // Method to synchronize email with User
    public void syncEmailFromUser() {
        if (user != null) {
            this.email = user.getEmail();
        }
    }

    // The `user_id` is the primary key in the database, so no need to explicitly set it here
    // It's part of the `User` object (which represents the one-to-one relationship)
}