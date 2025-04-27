package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostIdAndDeleteFlagIsFalse(Long postId);

    Page<Post> findAllByDeleteFlagIsFalseAndCensorFlagIsTrueOrderByCreateDateDesc(Pageable pageable);

    Page<Post> findAllByUser_UserIdAndGroupIsNullAndDeleteFlagIsFalse(Long userId, Pageable pageable);

    long countByUser_UserIdAndGroupIsNullAndDeleteFlagIsFalse(Long userId);
}
