package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostRequest {

    @NotBlankConstraint(name = "ID bài viết")
    Long postId;

    @NotBlankConstraint(name = "Nội dung bài viết")
    String content;

    @NotBlankConstraint(name = "Trạng thái bài viết")
    Byte status;

    List<UpdateMediaRequest> medias;
}
