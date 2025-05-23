package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.friend.AddFriendRequest;
import com.graduate.be_txnd_fanzone.dto.friend.FriendResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendReceivedResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendSentResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.FriendMapper;
import com.graduate.be_txnd_fanzone.model.Friend;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.FriendRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendService {

    FriendRepository friendRepository;
    FriendMapper friendMapper;
    UserRepository userRepository;
    SecurityUtil securityUtil;

    public void addFriendRequest(AddFriendRequest request) {
        //check add friend is existing
        boolean isExists = friendRepository
                .existsBySender_UserIdAndReceiver_UserIdAndDeleteFlagIsFalse(request.getSenderId(), request.getReceiverId());

        if (isExists) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_EXISTED);
        }

        User sender = userRepository.findByUserIdAndDeleteFlagIsFalse(request.getSenderId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findByUserIdAndDeleteFlagIsFalse(request.getReceiverId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Friend friend = new Friend();
        friend.setSender(sender);
        friend.setReceiver(receiver);
        friend.setStatus((byte) 0);
        friend.setDeleteFlag(false);
        friendRepository.save(friend);
    }

    public void acceptAddFriendRequest(Long userId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Friend friend = friendRepository.getAddFriendByUserIdAndUserLogin((byte) 0, userId, userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        if (friend.getReceiver().getUserId().equals(userLoginId)) {
            friend.setStatus((byte) 1);
            friendRepository.save(friend);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public void rejectAddFriendRequest(Long userId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Friend friend = friendRepository.getAddFriendByUserIdAndUserLogin((byte) 0, userId, userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        if (friend.getReceiver().getUserId().equals(userLoginId)) {
            friendRepository.delete(friend);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public void deleteAddFriendRequest (Long userId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Friend friend = friendRepository.getAddFriendByUserIdAndUserLogin((byte) 0, userId, userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        if (friend.getSender().getUserId().equals(userLoginId)) {
            friendRepository.delete(friend);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public void deleteFriend (Long userId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Friend friend = friendRepository.getAddFriendByUserIdAndUserLogin((byte) 1, userId, userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
        if (friend.getSender().getUserId().equals(userLoginId)) {
            friendRepository.delete(friend);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public List<ListAddFriendSentResponse> getAddFriendSent(int page, int limit) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createDate").descending());
        List<Friend> addFriends = friendRepository
                .findAllBySender_UserIdAndStatusAndDeleteFlagIsFalse(userLoginId, (byte) 0, pageable).getContent();

        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        return addFriends.stream().map(response -> {
            ListAddFriendSentResponse friendResponse = friendMapper.toListAddFriendSentResponse(response);
            friendResponse.setSeenAt(prettyTime.format(response.getCreateDate()));
            return friendResponse;
        }).toList();
    }

    public List<ListAddFriendReceivedResponse> getAddFriendReceived(int page, int limit) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createDate").descending());
        List<Friend> addFriends = friendRepository
                .findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(userLoginId, (byte) 0, pageable).getContent();

        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        return addFriends.stream().map(response -> {
            ListAddFriendReceivedResponse friendResponse = friendMapper.toListAddFriendReceivedResponse(response);
            friendResponse.setSeenAt(prettyTime.format(response.getCreateDate()));
            return friendResponse;
        }).toList();
    }

    public List<FriendResponse> getListFriend(int page, int limit) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<Friend> friendList = friendRepository.getListFriends(userLoginId, (byte) 1, pageable).getContent();
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));

        return friendList.stream().map(friend -> {
            FriendResponse friendResponse = new FriendResponse();
            if (friend.getReceiver().getUserId().equals(userLoginId)) {
                friendResponse.setUserId(friend.getSender().getUserId());
                friendResponse.setFullName(friend.getSender().getFirstName() + " " + friend.getSender().getLastName());
                friendResponse.setAvatar(friend.getSender().getAvatar());
            } else {
                friendResponse.setUserId(friend.getReceiver().getUserId());
                friendResponse.setFullName(friend.getReceiver().getFirstName() + " " + friend.getReceiver().getLastName());
                friendResponse.setAvatar(friend.getReceiver().getAvatar());
            }
            friendResponse.setFriendAt(prettyTime.format(friend.getUpdateDate()));
            friendResponse.setFriendId(friend.getFriendId());
            return friendResponse;
        }).collect(Collectors.toList());
    }
}
