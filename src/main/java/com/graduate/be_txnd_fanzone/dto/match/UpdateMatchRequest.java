package com.graduate.be_txnd_fanzone.dto.match;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMatchRequest {

    @NotBlankConstraint(name = "ID trận đấu")
    Long matchId;

    @NotBlankConstraint(name = "ID CLB đối thủ")
    Long clubId;

    @NotBlankConstraint(name = "Thông tin sân nhà")
    Boolean isHome;

    @NotBlankConstraint(name = "THời gian thi đấu")
    LocalDateTime matchDate;

    @NotBlankConstraint(name = "Thông tin giải đấu")
    String tournament;

    @NotBlankConstraint(name = "Thông tin vòng đấu")
    String round;
}
