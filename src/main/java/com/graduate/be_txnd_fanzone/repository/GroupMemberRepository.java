package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.GroupMember;
import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    boolean existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(Long userId,Long groupId, Byte memberRole);

    boolean existsByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(Long userId,Long groupId);

    Optional<GroupMember> findByGroupMemberIdAndDeleteFlagIsFalse(Long groupMemberId);

    @Query("SELECT gm.group.groupId, COUNT(gm) " +
            "FROM GroupMember gm " +
            "WHERE " +
            "   (gm.group.groupId IN :groupIds )" +
            "   AND gm.approved = true " +
            "GROUP BY gm.group.groupId")
    List<Object[]> countMembersForGroupIds(@Param("groupIds") List<Long> groupIds);

    long countByGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(Long groupId);

    Optional<GroupMember> findByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(Long userId, Long groupId);

    Page<GroupMember> findByGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(Long groupId, Pageable pageable);

    Optional<GroupMember> findByUser_UserIdAndGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(Long userId, Long groupId);

    @Query("SELECT gm " +
            "FROM GroupMember gm " +
            "WHERE gm.group.groupId = :groupId " +
            "AND (gm.memberRole = 1 OR gm.memberRole = 2) " +
            "ORDER BY gm.memberRole")
    Page<GroupMember> findGroupManager(@Param("groupId") Long groupId, Pageable pageable);

    Page<GroupMember> findAllByGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalseOrderByCreateDateDesc(Long groupId, Pageable pageable);

}
