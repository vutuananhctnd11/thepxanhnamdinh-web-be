package com.graduate.be_txnd_fanzone.dto.friend;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddFriendRequest {

    @NotBlankConstraint(name = "Mã người gửi")
    Long senderId;

    @NotBlankConstraint(name = "Mã người nhận")
    Long receiverId;

}
