package com.graduate.be_txnd_fanzone.dto.reaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReactionRequest {

    Long postId;
    Long userId;
}
