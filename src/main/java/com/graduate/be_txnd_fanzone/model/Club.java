package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "club")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Club extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id", nullable = false)
    Long clubId;

    @Column(name = "club_name", nullable = false)
    String clubName;

    @Column(name = "description")
    String description;

    @Column(name = "stadium", nullable = false)
    String stadium;

    @Column(name = "logo", nullable = false)
    String logo;

}
