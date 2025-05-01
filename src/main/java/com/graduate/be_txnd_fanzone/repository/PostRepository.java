package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostIdAndDeleteFlagIsFalse(Long postId);

    Page<Post> findAllByUser_UserIdAndCensorFlagIsTrueAndGroupIsNullAndDeleteFlagIsFalse(Long userId, Pageable pageable);

    long countByUser_UserIdAndGroupIsNullAndDeleteFlagIsFalse(Long userId);

    Page<Post> findAllByGroup_GroupIdAndCensorFlagIsTrueAndDeleteFlagIsFalse(Long groupId, Pageable pageable);

    List<Post> findAllByDeleteFlagIsFalseAndCensorFlagIsTrueOrderByCreateDateDesc ();

    Page<Post> findAllByUser_UserIdAndStatusAndCensorFlagIsTrueAndGroupIsNullAndDeleteFlagIsFalse(Long userId, Byte status, Pageable pageable);
}
