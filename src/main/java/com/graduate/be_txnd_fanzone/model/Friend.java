package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "friend")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Friend extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id", nullable = false)
    Long friendId;

    @Column(name = "status", nullable = false)
    String status;


}
