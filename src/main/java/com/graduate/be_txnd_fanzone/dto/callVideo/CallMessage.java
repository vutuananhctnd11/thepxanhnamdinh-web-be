package com.graduate.be_txnd_fanzone.dto.callVideo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallMessage {

    String type;
    Long fromUserId;
    Long toUserId;
    String channel;
}
