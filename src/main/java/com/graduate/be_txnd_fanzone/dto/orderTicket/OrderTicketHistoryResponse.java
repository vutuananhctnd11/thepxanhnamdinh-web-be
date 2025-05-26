package com.graduate.be_txnd_fanzone.dto.orderTicket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketHistoryResponse {

    Long orderTicketId;
    LocalDateTime createDate;
    String status;
    Integer totalTicket;
    Long totalPrice;
}
