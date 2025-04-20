package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.post.*;
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

    @Mapping(source = "group.groupId", target = "groupId")
    @Mapping(source = "group.groupName", target = "groupName")
    NewsFeedResponse toNewsFeedResponse(Post post);


}
