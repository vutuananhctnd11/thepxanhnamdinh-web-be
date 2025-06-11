package com.graduate.be_txnd_fanzone.dto.ticket;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Size.SizeConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTicketInfoRequest {

    @NotBlankConstraint(name = "Giá vé")
    Long price;

    @NotBlankConstraint(name = "Vị trí khán đài")
    String position;

    String note;

    @NotBlankConstraint(name = "Số lượng vé")
    @SizeConstraint(min = 0, max = 10, name = "Số lượng vé")
    Integer quantity;
}
