package com.graduate.be_txnd_fanzone.dto.orderTicket;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Length.LengthConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketInfoRequest {

    @NotBlankConstraint(name = "ID vé")
    Long ticketId;

    @NotBlankConstraint(name = "Số lượng vé")
    @LengthConstraint(min = 0, max = 10, name = "Số lượng vé")
    Integer quantity;
}
