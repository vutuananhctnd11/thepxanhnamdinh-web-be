package com.graduate.be_txnd_fanzone.dto.club;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubResponse {

    Long clubId;
    String clubName;
    String stadium;
    String logo;
    String description;
}
