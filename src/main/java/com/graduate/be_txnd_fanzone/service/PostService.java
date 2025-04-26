package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.post.*;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MediaMapper;
import com.graduate.be_txnd_fanzone.mapper.PostMapper;
import com.graduate.be_txnd_fanzone.model.Group;
import com.graduate.be_txnd_fanzone.model.Media;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.GroupMemberRepository;
import com.graduate.be_txnd_fanzone.repository.MediaRepository;
import com.graduate.be_txnd_fanzone.repository.PostRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostMapper postMapper;
    MediaMapper mediaMapper;
    PostRepository postRepository;
    UserRepository userRepository;
    MediaService mediaService;
    GroupMemberRepository groupMemberRepository;
    SecurityUtil securityUtil;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(usernameLogin)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //create news feed
        Post post = postMapper.toPost(request);
        post.setUser(userLogin);
        postRepository.save(post);

        //create list media
        List<Media> medias = request.getMedias().stream().map(createMediaRequest -> {
            Media media = mediaService.createMedia(createMediaRequest);
            media.setPost(post);
            return media;
        }).collect(Collectors.toList());

        //set list media for news feed
        post.setMedias(medias);
        postRepository.save(post);

        CreatePostResponse response = postMapper.toCreatePostResponse(post);
        response.setMedia(medias.stream().map(mediaMapper::toMediaResponse).toList());
        return response;
    }

    public UpdatePostResponse updatePost(UpdatePostRequest request) {
        Post postUpdate = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        //update list media
        List<Media> mediasUpdate = request.getMedias().stream().map(mediaService::updateMedia).toList();

        //update post
        postUpdate = postMapper.updatePost(request, postUpdate);
        postUpdate.setMedias(mediasUpdate);
        postRepository.save(postUpdate);

        // mapper to update response
        UpdatePostResponse response = postMapper.toUpdatePostResponse(postUpdate);
        response.setMedias(mediasUpdate.stream().map(mediaMapper::toMediaResponse).toList());
        return response;
    }

    public void changeStatus(UpdatePostStatusRequest request) {
        Post postUpdate = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Long userLoginId = securityUtil.getCurrentUserId();
        if (postUpdate.getUser().getUserId().equals(userLoginId)) {
            postUpdate.setStatus(request.getStatus());
            postRepository.save(postUpdate);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public void softDeleteOrRestorePost(Long postId, Boolean isDelete) {
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.setDeleteFlag(isDelete);
        postRepository.save(post);
    }

    public void approveGroupPost(Long postId) {
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Group group = post.getGroup();
        Long userLoginId = securityUtil.getCurrentUserId();

        if (group != null) {
            if (isAdminOrModeratorGroup(userLoginId, group.getGroupId())) {
                post.setCensorFlag(true);
                postRepository.save(post);
            } else {
                throw new CustomException(ErrorCode.NO_PERMISSION);
            }
        } else {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }

    }

    public void rejectGroupPost(Long postId) {
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Group group = post.getGroup();
        Long userLoginId = securityUtil.getCurrentUserId();

        if (group != null) {
            if (isAdminOrModeratorGroup(userLoginId, group.getGroupId())) {
                postRepository.delete(post);
            } else {
                throw new CustomException(ErrorCode.NO_PERMISSION);
            }
        }
        // will process continue
    }

    private boolean isAdminOrModeratorGroup(Long userId, Long groupId) {
        boolean isAdminGroup = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(userId, groupId, (byte) 2);
        boolean isModeratorGroup = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(userId, groupId, (byte) 1);
        return isAdminGroup || isModeratorGroup;
    }

    public List<NewsFeedResponse> getNewsFeed(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createDate").descending());
        List<Post> posts = postRepository.findAllByDeleteFlagIsFalseAndCensorFlagIsTrueOrderByCreateDateDesc(pageable).getContent();
        Long userLoginId = securityUtil.getCurrentUserId();

        User userLogin = userRepository.findByUserIdAndDeleteFlagIsFalse(userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Long> joinedGroupIds = userLogin.getGroupMembers().stream()
                .map(groupMember -> groupMember.getGroup().getGroupId()).toList();

        List<Post> filteredPosts = posts.stream().filter(post -> {
            Group group = post.getGroup();
            return group == null || joinedGroupIds.contains(group.getGroupId());
        }).toList();

        return filteredPosts.stream().map(post -> {
            NewsFeedResponse newsFeedResponse = postMapper.toNewsFeedResponse(post);
            PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
            String seenAt = prettyTime.format(post.getCreateDate());
            newsFeedResponse.setSeenAt(seenAt);
            newsFeedResponse.setMedias(post.getMedias().stream().map(mediaMapper::toMediaResponse).toList());
            return newsFeedResponse;
        }).toList();
    }

    public NewsFeedResponse getPostByPostId (Long postId) {
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return postMapper.toNewsFeedResponse(post);
    }


}
