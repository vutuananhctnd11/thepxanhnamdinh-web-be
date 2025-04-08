package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "player")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Player extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", nullable = false)
    Long playerId;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name",nullable = false)
    String lastName;

    @Column(name = "name_in_shirt")
    String nameInShirt;

    @Column(name = "shirt_number")
    Integer shirtNumber;

    @Column(name = "age")
    Byte age;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "height")
    Float height;

    @Column(name = "weight")
    Integer weight;

    @Column(name = "nationality", nullable = false)
    String nationality;

    @Column(name = "position", nullable = false)
    String position;

    @Column(name = "goal")
    Integer goal;

    @Column(name = "assist")
    Integer assist;

    @Column(name = "avatar_image", nullable = false)
    String avatarImage;

    @Column(name = "full_body_image")
    String fullBodyImage;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;

}
