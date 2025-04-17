package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "order_ticket_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketDetail extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_ticket_detail_id", nullable = false)
    String orderTicketDetailId;

    @ManyToOne
    @JoinColumn(name = "order_ticket_id")
    OrderTicket orderTicket;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    Ticket ticket;

}
