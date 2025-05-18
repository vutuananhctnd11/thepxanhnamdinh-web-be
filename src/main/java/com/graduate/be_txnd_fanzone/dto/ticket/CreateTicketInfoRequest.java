package com.graduate.be_txnd_fanzone.dto.ticket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTicketInfoRequest {

    Long price;
    String position;
    String note;
    Integer quantity;
}
