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

    List<Friend> findAllBySender_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status);

    Page<Friend> findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);

    List<Friend> findAllByReceiver_UserIdAndStatusAndDeleteFlagIsFalse(Long userId, Byte status);

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
            SELECT
                CASE
                    WHEN f.sender.userId = :userId THEN f.receiver.userId
                    ELSE f.sender.userId
                END
            FROM Friend f
            WHERE
                (f.sender.userId = :userId OR f.receiver.userId = :userId)
                AND f.status = :status
                AND f.deleteFlag = false
            """)
    List<Long> getListFriendIds(@Param("userId") Long userId, @Param("status") Byte status);


    @Query("SELECT f FROM Friend f " +
            "WHERE f.status = :status " +
            "   AND(" +
            "       (f.sender.userId = :userId1 AND f.receiver.userId = :userId2) " +
            "   OR (f.sender.userId = :userId2 AND f.receiver.userId = :userId1))")
    Optional<Friend> getAddFriendByUserIdAndUserLogin(Byte status, Long userId1, Long userId2);

    @Query("""
            SELECT f.sender.userId, COUNT(f)
            FROM Friend f
            WHERE f.status = 1 
                AND f.deleteFlag = false 
                AND f.receiver.userId IN :userIds
                GROUP BY f.sender.userId
            UNION
            SELECT f.receiver.userId, COUNT(f)
            FROM Friend f
            WHERE f.status = 1 
                AND f.deleteFlag = false 
                AND f.sender.userId IN :userIds
                GROUP BY f.receiver.userId
            """)
    List<Object[]> countFriendsByListUserIds(@Param("userIds") List<Long> userIds);



}
