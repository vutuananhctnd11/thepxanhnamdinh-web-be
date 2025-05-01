package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.media.CreateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.post.*;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MediaMapper;
import com.graduate.be_txnd_fanzone.mapper.PostMapper;
import com.graduate.be_txnd_fanzone.model.*;
import com.graduate.be_txnd_fanzone.repository.*;
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

import java.util.*;
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
    ReactionRepository reactionRepository;
    CommentRepository commentRepository;
    GroupRepository groupRepository;
    FriendRepository friendRepository;
    MediaRepository mediaRepository;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(usernameLogin)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //create news feed
        Post post = postMapper.toPost(request);
        post.setUser(userLogin);
        if (request.getGroupId() != null) {
            Group group = groupRepository.findGroupsByGroupIdAndDeleteFlagIsFalse(request.getGroupId())
                    .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
            post.setGroup(group);
            post.setCensorFlag(!group.getCensorPost());
        } else {
            post.setCensorFlag(true);
        }
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

        List<Long> listMediasIds = mediaRepository.getListMediaIdsOfPost(postUpdate.getPostId());

        //update list media
        List<Media> mediasUpdate = new ArrayList<>();
        for (UpdateMediaRequest updateMediaRequest : request.getMedias()) {
            if (updateMediaRequest.getMediaId() != null && listMediasIds.contains(updateMediaRequest.getMediaId())) {
                listMediasIds.remove(updateMediaRequest.getMediaId());
            } else if (updateMediaRequest.getMediaId() == null) {
                CreateMediaRequest createMediaRequest = new CreateMediaRequest();
                createMediaRequest.setLinkCloud(updateMediaRequest.getLinkCloud());
                createMediaRequest.setType(updateMediaRequest.getType());
                Media media = mediaService.createMedia(createMediaRequest);
                media.setPost(postUpdate);
                mediasUpdate.add(media);
            }
        }
        //remove media
        listMediasIds.forEach(mediaService::deleteMedia);

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
        List<Post> allPosts = postRepository.findAllByDeleteFlagIsFalseAndCensorFlagIsTrueOrderByCreateDateDesc();

        //get user login
        Long userLoginId = securityUtil.getCurrentUserId();
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //get list friend id
        List<Long> friendIds = friendRepository.getListFriendIds(userLoginId);
        //get list joined group
        List<Long> joinedGroupIds = groupRepository.findGroupIdsByUserId(userLoginId);
        //filter remove post
        List<Post> filteredPosts = allPosts.stream().filter(post -> {
            Group group = post.getGroup();
            boolean isFriendPost = friendIds.contains(post.getUser().getUserId());
            boolean isPublicPost = post.getStatus() == 1;
            boolean isUserPost = post.getUser().getUserId().equals(userLoginId);
            boolean isInGroup = group != null && joinedGroupIds.contains(group.getGroupId());

            return isFriendPost || isPublicPost || isUserPost || isInGroup;
        }).collect(Collectors.toList());

        int start = (page - 1) * limit;
        int end = Math.min(start + limit, filteredPosts.size());

        List<Post> pagedPosts = filteredPosts.subList(start, end);

        return convertToListNewsFeedResponse(pagedPosts, userLoginId);
    }

    //convert list post from repository to list NewsFeedResponse
    private List<NewsFeedResponse> convertToListNewsFeedResponse(List<Post> posts, Long userId) {
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        List<Long> postIds = posts.stream().map(Post::getPostId).toList();
        //get list total reaction by post id
        Map<Long, Long> reactCounts = mapListObjectToMap(reactionRepository.countReactionsForPostIds(postIds));
        //get list total comment by post id
        Map<Long, Long> commentCounts = mapListObjectToMap(commentRepository.countCommentsForPostIds(postIds));
        //get list post liked by user
        Set<Long> likedPostIds = reactionRepository.findPostIdsLikedByUser(postIds, userId);

        return posts.stream().map(post -> {
            NewsFeedResponse newsFeedResponse = postMapper.toNewsFeedResponse(post);
            String seenAt = prettyTime.format(post.getCreateDate());
            newsFeedResponse.setSeenAt(seenAt);
            newsFeedResponse.setMedias(post.getMedias().stream().map(mediaMapper::toMediaResponse).toList());

            Long postId = post.getPostId();
            newsFeedResponse.setReactCount(reactCounts.getOrDefault(postId, 0L));
            newsFeedResponse.setCommentCount(commentCounts.getOrDefault(postId, 0L));
            newsFeedResponse.setLiked(likedPostIds.contains(postId));
            return newsFeedResponse;
        }).toList();
    }

    // convert list object from repository to Map
    private Map<Long, Long> mapListObjectToMap(List<Object[]> listObjects) {
        return listObjects.stream().collect(Collectors.toMap(
                row -> (Long) row[0], row -> (Long) row[1]
        ));
    }

    public NewsFeedResponse getPostByPostId(Long postId) {
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        NewsFeedResponse response = postMapper.toNewsFeedResponse(post);
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        String seenAt = prettyTime.format(post.getCreateDate());
        response.setSeenAt(seenAt);
        response.setMedias(post.getMedias().stream().map(mediaMapper::toMediaResponse).toList());

        response.setReactCount(reactionRepository.countByPost_PostId(postId));
        response.setCommentCount(commentRepository.countByPost_PostIdAndDeleteFlagIsFalse(postId));
        response.setLiked(reactionRepository.existsByPost_PostIdAndUser_UserId(postId, securityUtil.getCurrentUserId()));
        return response;
    }

    public PageableListResponse<NewsFeedResponse> getListPostByUserId(int page, int limit, Long userId) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createDate").descending());
        Long userLoginId = securityUtil.getCurrentUserId();
        boolean isOwner = userLoginId.equals(userId);
        boolean isFriend = isOwner || friendRepository.findAcceptedFriendRelation(userId, userLoginId).isPresent();

        List<Post> posts;
        if (isFriend) {
            posts = postRepository.findAllByUser_UserIdAndCensorFlagIsTrueAndGroupIsNullAndDeleteFlagIsFalse(userId, pageable).getContent();
        } else {
            posts = postRepository.findAllByUser_UserIdAndStatusAndCensorFlagIsTrueAndGroupIsNullAndDeleteFlagIsFalse(userId, (byte) 1, pageable).getContent();
        }
        PageableListResponse<NewsFeedResponse> response = new PageableListResponse<>();
        response.setListResults(convertToListNewsFeedResponse(posts, userId));
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) Math.ceil((double) posts.size() / limit));
        return response;
    }

    public PageableListResponse<NewsFeedResponse> getListPostByGroupId(int page, int limit, Long groupId) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createDate").descending());
        PageableListResponse<NewsFeedResponse> response = new PageableListResponse<>();
        Group group = groupRepository.findGroupsByGroupIdAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        if (group.getType() != 1) {
            response.setListResults(new ArrayList<>());
            return response;
        }
        List<Post> posts = postRepository
                .findAllByGroup_GroupIdAndCensorFlagIsTrueAndDeleteFlagIsFalse(groupId, pageable).getContent();
        response.setListResults(convertToListNewsFeedResponse(posts, securityUtil.getCurrentUserId()));
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) Math.ceil((double) posts.size() / limit));
        return response;

    }


}
