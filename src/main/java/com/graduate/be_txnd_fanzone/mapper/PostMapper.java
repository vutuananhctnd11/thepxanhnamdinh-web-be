package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.post.CreatePostRequest;
import com.graduate.be_txnd_fanzone.dto.post.CreatePostResponse;
import com.graduate.be_txnd_fanzone.dto.post.UpdatePostRequest;
import com.graduate.be_txnd_fanzone.dto.post.UpdatePostResponse;
import com.graduate.be_txnd_fanzone.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "reportFlag", constant = "false")
    @Mapping(target = "censorFlag", constant = "true")
    Post toPost(CreatePostRequest request);

    CreatePostResponse toCreatePostResponse(Post post);

    Post updatePost(UpdatePostRequest request, @MappingTarget Post post);

    UpdatePostResponse toUpdatePostResponse(Post post);


}
