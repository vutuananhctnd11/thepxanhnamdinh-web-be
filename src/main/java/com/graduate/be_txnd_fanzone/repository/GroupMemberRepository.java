package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.GroupMember;
import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    boolean existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(Long userId,Long groupId, Byte memberRole);

    Optional<GroupMember> findByGroupMemberIdAndDeleteFlagIsFalse(Long groupMemberId);

    Long user(User user);
}
