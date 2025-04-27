package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.reaction.ReactionResponse;
import com.graduate.be_txnd_fanzone.model.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userFullName", expression = "java(reaction.getUser().getFirstName() + \" \" + reaction.getUser().getLastName())")
    @Mapping(target = "postId", source = "post.postId")
    ReactionResponse toReactionResponse(Reaction reaction);
}
