package com.graduate.be_txnd_fanzone.dto.orderTicket;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketHistoryResponse {

    Long orderTicketId;
    LocalDateTime createDate;
    String status;
    Long totalTicket;
    Long totalPrice;
}
