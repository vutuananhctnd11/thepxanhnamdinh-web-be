package com.graduate.be_txnd_fanzone.dto.club;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClubRequest {

    @NotBlankConstraint(name = "Tên CLB")
    String clubName;

    @NotBlankConstraint(name = "Tên sân vận động")
    String stadium;

    @NotBlankConstraint(name = "Logo CLB")
    String logo;
}
