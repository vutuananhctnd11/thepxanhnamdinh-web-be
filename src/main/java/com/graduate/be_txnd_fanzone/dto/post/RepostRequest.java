package com.graduate.be_txnd_fanzone.dto.post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepostRequest {

    Long postId;
}
