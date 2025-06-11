package com.graduate.be_txnd_fanzone.dto.report;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReportRequest {

    @NotBlankConstraint(name = "ID bài viết")
    Long postId;

    @NotBlankConstraint(name = "Lý do báo cáo bài viết")
    String reason;
}
