package com.graduate.be_txnd_fanzone.dto.group;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGroupRequest {

    String groupName;
    String description;
    Byte type;
    Boolean censorPost;
    Boolean censorMember;
    String avatarImage;
}
