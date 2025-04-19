package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.dto.media.MediaResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostResponse {

    Long postId;
    String content;
    Byte type;
    Byte status;
    Boolean censorFlag;
    List<MediaResponse> medias;
}
