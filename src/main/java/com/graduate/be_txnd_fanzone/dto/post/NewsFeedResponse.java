package com.graduate.be_txnd_fanzone.dto.post;

import com.graduate.be_txnd_fanzone.dto.media.MediaResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsFeedResponse {

    Long postId;
    Long userId;
    String userFullName;
    String avatar;
    String content;
    Byte type;
    Byte status;
    String seenAt;
    Long groupId;
    String groupName;
    Long commentCount;
    Long reactCount;
    Boolean liked;
    List<MediaResponse> medias;

}
