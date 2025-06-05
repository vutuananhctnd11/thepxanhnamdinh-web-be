package com.graduate.be_txnd_fanzone.dto.group;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGroupRequest {

    @NotBlankConstraint(name = "ID nhóm")
    Long groupId;

    @NotBlankConstraint(name = "Tên nhóm")
    String groupName;

    @NotBlankConstraint(name = "Phân loại nhóm")
    Byte type;

    @NotBlankConstraint(name = "Kiểm duyệt bài viết")
    Boolean censorPost;

    @NotBlankConstraint(name = "Kiểm duyệt thành viên")
    Boolean censorMember;

    @NotBlankConstraint(name = "Ảnh đại diện nhóm")
    String avatarImage;

    String description;
}
