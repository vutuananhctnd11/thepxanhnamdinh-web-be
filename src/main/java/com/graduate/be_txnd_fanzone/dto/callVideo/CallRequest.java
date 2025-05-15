package com.graduate.be_txnd_fanzone.dto.callVideo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallRequest {

    Long callerId;
    Long receiverId;

}
