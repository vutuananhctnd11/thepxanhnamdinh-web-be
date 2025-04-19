package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostRequest {

    Long postId;
    String content;
    Byte status;
    List<UpdateMediaRequest> medias;
}
