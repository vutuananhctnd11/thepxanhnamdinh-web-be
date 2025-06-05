package com.graduate.be_txnd_fanzone.dto.groupMember;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddGroupMemberRequest {

    @NotBlankConstraint(name = "ID người dùng vào nhóm")
    Long userId;

    @NotBlankConstraint(name = "ID nhóm")
    Long groupId;
}
