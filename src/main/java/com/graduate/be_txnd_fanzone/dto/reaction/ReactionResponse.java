package com.graduate.be_txnd_fanzone.dto.reaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionResponse {

    Long reactionId;
    Long userId;
    String userFullName;
    Long postId;
}
