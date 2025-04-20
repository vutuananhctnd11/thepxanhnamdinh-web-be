package com.graduate.be_txnd_fanzone.dto.group;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupResponse {

    Long groupId;
    String groupName;
    Byte type;
    Boolean censorPost;
    Boolean censorMember;
    String avatarImage;
}
