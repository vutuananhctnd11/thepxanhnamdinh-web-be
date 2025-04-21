package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsBySender_UserIdAndReceiver_UserIdAndDeleteFlagIsFalse(Long sender, Long receiver);

    Optional<Friend> findByFriendIdAndDeleteFlagIsFalse(Long friendId);

    Page<Friend> findAllBySender_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);

    Page<Friend> findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);
}
