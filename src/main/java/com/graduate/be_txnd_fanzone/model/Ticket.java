package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "ticket")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ticket_id", nullable = false)
    String ticketId;

    @Column(name = "price", nullable = false)
    Integer price;

    @Column(name = "stand_name", nullable = false)
    String standName;

    @Column(name = "status", nullable = false)
    String status;

    //Liên kết many to one với match
}
