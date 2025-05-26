package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.post.NewsFeedResponse;
import com.graduate.be_txnd_fanzone.dto.report.CreateReportRequest;
import com.graduate.be_txnd_fanzone.dto.report.ReportResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.PostMapper;
import com.graduate.be_txnd_fanzone.mapper.ReportMapper;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.ReportPost;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.*;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {

    ReportRepository reportRepository;
    ReportMapper reportMapper;
    PostMapper postMapper;
    SecurityUtil securityUtil;
    UserRepository userRepository;
    PostRepository postRepository;
    ReactionRepository reactionRepository;
    CommentRepository commentRepository;


    public PageableListResponse<ReportResponse> getListReport (int page, int limit) {
        PageableListResponse<ReportResponse> response = new PageableListResponse<>();
        Pageable pageable = PageRequest.of(page-1 , limit, Sort.by("createDate").descending());
        Page<ReportPost> reports = reportRepository.findByStatus((byte) 0, pageable);
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) reports.getTotalPages());
        response.setListResults(reports.getContent().stream().map(reportPost -> {
            ReportResponse reportResponse = reportMapper.toReportResponse(reportPost);
            Post post = reportPost.getPost();
            NewsFeedResponse postResponse = postMapper.toNewsFeedResponse(post);
            PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
            postResponse.setSeenAt(prettyTime.format(post.getCreateDate()));
            postResponse.setReactCount(reactionRepository.countByPost_PostId(post.getPostId()));
            postResponse.setCommentCount(commentRepository.countByPost_PostIdAndDeleteFlagIsFalse(post.getPostId()));
            postResponse.setLiked(reactionRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), securityUtil.getCurrentUserId()));
            reportResponse.setPost(postResponse);
            return reportResponse;
        }).toList());
        return response;
    }

    public void createReport (CreateReportRequest request) {
        Long userLoginId = securityUtil.getCurrentUserId();
        User userLogin = userRepository.findByUserIdAndDeleteFlagIsFalse(userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.setReportFlag(true);
        postRepository.save(post);

        ReportPost reportPost = new ReportPost();
        reportPost.setReason(request.getReason());
        reportPost.setStatus((byte) 0);
        reportPost.setUser(userLogin);
        reportPost.setPost(post);
        reportRepository.save(reportPost);
    }

    public void approveReport (Long reportId) {
        ReportPost reportPost = reportRepository.findByReportId(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        reportPost.setStatus((byte) 1);
        reportRepository.save(reportPost);

        Post post = reportPost.getPost();
        post.setReportFlag(false);
        postRepository.save(post);
    }

    public void rejectReport (Long reportId) {
        ReportPost reportPost = reportRepository.findByReportId(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        reportRepository.delete(reportPost);

        Post post = reportPost.getPost();
        post.setReportFlag(false);
        postRepository.save(post);
    }

}
