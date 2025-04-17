package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Table(name = "order_ticket")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicket extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_ticket_id", nullable = false)
    Long orderTicketId;

    @Column(name = "status", nullable = false)
    String status;

    @OneToMany(mappedBy = "orderTicket")
    List<OrderTicketDetail> orderTicketDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
