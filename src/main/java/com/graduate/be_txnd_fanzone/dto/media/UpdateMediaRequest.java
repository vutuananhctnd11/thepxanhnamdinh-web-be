package com.graduate.be_txnd_fanzone.dto.media;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMediaRequest {

    Long mediaId;
    String linkCloud;
    Byte type;
}
