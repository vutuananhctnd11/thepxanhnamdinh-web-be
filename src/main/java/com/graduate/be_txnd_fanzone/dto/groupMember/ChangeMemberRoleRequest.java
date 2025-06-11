package com.graduate.be_txnd_fanzone.dto.groupMember;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeMemberRoleRequest {

    @NotBlankConstraint(name = "ID nhóm")
    Long groupId;

    @NotBlankConstraint(name = "ID người dùng trong nhóm")
    Long userId;

    @NotBlankConstraint(name = "Quyền của thành viên nhóm")
    Byte memberRole;
}
