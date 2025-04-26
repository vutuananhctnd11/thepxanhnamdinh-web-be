package com.graduate.be_txnd_fanzone.dto.comment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {

    Long commentId;
    Long postId;
    Long userId;
    String userFullName;
    String avatar;
    String content;
    String createdAt;
    Boolean isUpdate;
}
