package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(Long groupId);

    Page<Group> findAllByApprovedIsTrueAndDeleteFlagIsFalse(Pageable pageable);

    Page<Group> findAllByTypeAndApprovedIsTrueAndDeleteFlagIsFalse(Byte type, Pageable pageable);

    Optional<Group> findByGroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(Long groupId);

    @Query("SELECT g " +
            "FROM Group g " +
            "   JOIN GroupMember gm ON g.groupId = gm.group.groupId " +
            "   JOIN User u ON gm.user.userId = u.userId " +
            "WHERE u.userId = :userId " +
            "   AND g.approved = true" +
            "   AND g.deleteFlag = false")
    Page<Group> findGroupsByUserId(@Param("userId") Long userId, Pageable pageable);

    Optional<Group> findGroupsByGroupIdAndDeleteFlagIsFalse(Long groupId);

    @Query("""
            SELECT g.groupId
            FROM Group g
                JOIN GroupMember gm ON g.groupId = gm.group.groupId
                JOIN User u ON gm.user.userId = u.userId
            WHERE u.userId = :userId
                AND g.deleteFlag = false
                AND g.approved = true
                AND gm.approved = true
    """)
    List<Long> findGroupIdsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT g
            FROM Group g
            WHERE
                LOWER(g.groupName) LIKE LOWER(CONCAT('%', :search , '%'))
                AND g.deleteFlag=false
                AND g.approved = true
            """)
    Page<Group> searchGroups(@Param("search") String search, Pageable pageable);

    @Query("""
            SELECT g
            FROM Group g
            WHERE
                g.type = 2
                AND g.deleteFlag = false
                AND g.approved = false
""")
    Page<Group> findFansGroupRequest(Pageable pageable);

}
