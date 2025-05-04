package com.graduate.be_txnd_fanzone.dto.search;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchGroupResponse {

    Long groupId;
    String groupName;
    String avatarImage;
    Byte type;
    Long totalMembers;
    Boolean isJoined;

}
