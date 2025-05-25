package com.graduate.be_txnd_fanzone.dto.report;


import com.graduate.be_txnd_fanzone.dto.post.NewsFeedResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {

    Long reportId;
    String reporterUsername;
    String reporterFullName;
    String reason;
    NewsFeedResponse post;
}
