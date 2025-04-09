package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "coach")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coach extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id", nullable = false)
    Long coachId;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name",nullable = false)
    String lastName;

    @Column(name = "age")
    Byte age;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "nationality", nullable = false)
    String nationality;

    @Column(name = "address")
    String address;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "image")
    String image;

    @Column(name = "position")
    String position;
}
