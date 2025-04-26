package com.graduate.be_txnd_fanzone.dto.comment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommentRequest {

    Long postId;
    String content;
}
