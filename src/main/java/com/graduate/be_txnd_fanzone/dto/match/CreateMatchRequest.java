package com.graduate.be_txnd_fanzone.dto.match;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMatchRequest {

    @NotBlankConstraint(name = "ID CLB đối thủ")
    Long clubId;

    @NotBlankConstraint(name = "Thông tin sân nhà")
    Boolean isHome;

    @NotBlankConstraint(name = "Thời gian trận đấu")
    LocalDateTime matchDate;

    @NotBlankConstraint(name = "Tên giải đấu")
    String tournament;

    @NotBlankConstraint(name = "Thông tin vòng đấu")
    String round;

}
