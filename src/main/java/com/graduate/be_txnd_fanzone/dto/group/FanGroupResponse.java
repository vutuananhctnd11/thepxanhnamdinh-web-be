package com.graduate.be_txnd_fanzone.dto.group;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FanGroupResponse {

    Long groupId;
    String groupName;
    LocalDateTime createDate;
    String createBy;
}
