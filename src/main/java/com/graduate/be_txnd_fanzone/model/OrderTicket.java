package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "order_ticket")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicket extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ticket_id", nullable = false)
    Long orderTicketId;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
}
