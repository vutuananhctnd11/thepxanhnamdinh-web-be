package com.graduate.be_txnd_fanzone.dto.groupMember;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddGroupMemberRequest {

    Long userId;
    Long groupId;
}
