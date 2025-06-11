package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.dto.media.CreateMediaRequest;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePostRequest {

    @NotBlankConstraint(name = "Nội dung bài viết")
    String content;

    @NotBlankConstraint(name = "Phân loại bài viết")
    Byte type;

    @NotBlankConstraint(name = "Trạng thái bài viết")
    Byte status;

    Long groupId;

    List<CreateMediaRequest> medias;

}
