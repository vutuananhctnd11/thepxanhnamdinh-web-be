package com.graduate.be_txnd_fanzone.dto.group;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupResponse {

    Long groupId;
    String groupName;
    String description;
    Byte type;
    Boolean censorPost;
    Boolean censorMember;
    String avatarImage;
    String createdDate;
    Long totalMembers;
}
