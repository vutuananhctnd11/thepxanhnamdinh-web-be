package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.post.NewsFeedResponse;
import com.graduate.be_txnd_fanzone.dto.report.CreateReportRequest;
import com.graduate.be_txnd_fanzone.dto.report.ReportResponse;
import com.graduate.be_txnd_fanzone.model.ReportPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "reporterUsername", source = "user.username")
    @Mapping(target = "reporterFullName", expression = "java(reportPost.getUser().getFirstName() + \" \" + reportPost.getUser().getLastName())")
    ReportResponse toReportResponse(ReportPost reportPost);
}