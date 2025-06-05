package com.graduate.be_txnd_fanzone.dto.comment;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCommentRequest {

    @NotBlankConstraint(name = "Mã bình luận")
    Long commentId;

    @NotBlankConstraint(name = "Nội dung bình luận")
    String content;
}
