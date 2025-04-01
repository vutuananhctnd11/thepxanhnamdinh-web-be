package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "order_ticket_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketDetail extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ticket_detail_id", nullable = false)
    String orderTicketDetailId;

    @Column(name = "total_ticket", nullable = false)
    Integer totalTicket;

    @Column(name = "total_price", nullable = false)
    Integer totalPrice;

    @Column(name = "status", nullable = false)
    String status;

}
