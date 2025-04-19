package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.post.*;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MediaMapper;
import com.graduate.be_txnd_fanzone.mapper.PostMapper;
import com.graduate.be_txnd_fanzone.model.Media;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.MediaRepository;
import com.graduate.be_txnd_fanzone.repository.PostRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostMapper postMapper;
    MediaMapper mediaMapper;
    PostRepository postRepository;
    MediaRepository mediaRepository;
    UserRepository userRepository;

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
            Media media = mediaMapper.toMedia(createMediaRequest);
            media.setPost(post);
            return mediaRepository.save(media);
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

        List<Media> mediasUpdate = request.getMedias().stream().map(updateMedia -> {
            Media media = mediaRepository.findById(updateMedia.getMediaId())
                    .orElseThrow(() -> new CustomException(ErrorCode.MEDIA_NOT_FOUND));
            media = mediaMapper.updateMedia(updateMedia, media);
             return mediaRepository.save(media);
        }).toList();
        postUpdate = postMapper.updatePost(request, postUpdate);
        postUpdate.setMedias(mediasUpdate);
        postRepository.save(postUpdate);

        UpdatePostResponse response = postMapper.toUpdatePostResponse(postUpdate);
        response.setMedias(mediasUpdate.stream().map(mediaMapper::toMediaResponse).toList());
        return response;
    }

    public void changeStatus(UpdatePostStatusRequest request){
    Post newsFeedUpdate = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    newsFeedUpdate.setStatus(request.getStatus());
    postRepository.save(newsFeedUpdate);
    }

    public void softDeleteOrRestorePost(Long postId, Boolean isDelete){
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.setDeleteFlag(isDelete);
        postRepository.save(post);
    }

    public void approveGroupPost(Long postId){
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.setCensorFlag(true);
        postRepository.save(post);
    }

    public void rejectGroupPost(Long postId){
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.setDeleteFlag(true);
        // will process continue
    }

}
