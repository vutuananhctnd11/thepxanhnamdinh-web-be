package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.friend.FriendResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendReceivedResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendSentResponse;
import com.graduate.be_txnd_fanzone.model.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "senderFirstName", source = "sender.firstName")
    @Mapping(target = "senderLastName", source = "sender.lastName")
    @Mapping(target = "receiverId", source = "receiver.userId")
    @Mapping(target = "receiverFirstName", source = "receiver.firstName")
    @Mapping(target = "receiverLastName", source = "receiver.lastName")
    FriendResponse toFriendResponse(Friend friend);


    @Mapping(target = "receiverId", source = "receiver.userId")
    @Mapping(target = "avatar", source = "receiver.avatar")
    @Mapping(target = "fullName", expression = "java(friend.getReceiver().getFirstName() + \" \" + friend.getReceiver().getLastName())")
    ListAddFriendSentResponse toListAddFriendSentResponse(Friend friend);

    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "avatar", source = "sender.avatar")
    @Mapping(target = "fullName", expression = "java(friend.getSender().getFirstName() + \" \" + friend.getSender().getLastName())")
    ListAddFriendReceivedResponse toListAddFriendReceivedResponse(Friend friend);
}
