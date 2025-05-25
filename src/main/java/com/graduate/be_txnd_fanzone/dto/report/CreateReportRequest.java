package com.graduate.be_txnd_fanzone.dto.report;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReportRequest {

    Long postId;
    String reason;
}
