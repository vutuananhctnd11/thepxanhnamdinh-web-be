package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostStatusRequest {

    @NotBlankConstraint(name = "ID bài viết")
    Long postId;

    @NotBlankConstraint(name = "Trạng thái bài viết")
    Byte status;
}
