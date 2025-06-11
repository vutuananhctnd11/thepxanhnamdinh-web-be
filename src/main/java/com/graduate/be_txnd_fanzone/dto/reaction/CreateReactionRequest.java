package com.graduate.be_txnd_fanzone.dto.reaction;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReactionRequest {

    @NotBlankConstraint(name = "ID bài viết")
    Long postId;

    @NotBlankConstraint(name = "ID người bày tỏ cảm xúc")
    Long userId;
}
