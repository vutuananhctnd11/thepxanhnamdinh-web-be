package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsBySender_UserIdAndReceiver_UserIdAndDeleteFlagIsFalse(Long sender, Long receiver);

    Optional<Friend> findByFriendIdAndDeleteFlagIsFalse(Long friendId);

    Page<Friend> findAllBySender_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);

    Page<Friend> findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);

    long countByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status);

    long countBySender_UserIdAndStatusAndDeleteFlagIsFalse(Long sender, Byte status);

    @Query("""
    SELECT f FROM Friend f
    WHERE\s
        (f.sender.userId = :userId\s
        AND f.status = :status\s
        AND f.deleteFlag = false)
      OR\s
      (f.receiver.userId = :userId\s
        AND f.status = :status\s
        AND f.deleteFlag = false)
    \s""")
    Page<Friend> getListFriends(@Param("userId") Long userId, @Param("status") Byte status, Pageable pageable);

    @Query("""
    SELECT f.friendId FROM Friend f
    WHERE\s
        (f.sender.userId = :userId\s
        AND f.status = 1 \s
        AND f.deleteFlag = false)
      OR\s
      (f.receiver.userId = :userId\s
        AND f.status = 1 \s
        AND f.deleteFlag = false)
    \s""")
    List<Long> getListFriendIds(@Param("userId") Long userId);
}
