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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    Long ticketId;

    @Column(name = "price", nullable = false)
    Integer price;

    @Column(name = "stand_name", nullable = false)
    String standName;

    @Column(name = "position")
    String position;

    @Column(name = "note")
    String note;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "status", nullable = false)
    String status;

    @ManyToOne
    @JoinColumn(name = "match_id")
    Match match;
}
