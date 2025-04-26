package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.comment.CommentResponse;
import com.graduate.be_txnd_fanzone.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userFullName", expression = "java(comment.getUser().getFirstName() + \" \" + comment.getUser().getLastName())")
    @Mapping(target = "avatar", source = "user.avatar")
    @Mapping(target = "isUpdate", ignore = true)
    CommentResponse toCommentResponse(Comment comment);
}
