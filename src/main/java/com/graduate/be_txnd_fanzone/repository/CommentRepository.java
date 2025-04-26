package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost_PostIdAndDeleteFlagIsFalseOrderByCreateDateDesc(Long postId, Pageable pageable);

    Optional<Comment> findByCommentIdAndDeleteFlagIsFalse(Long commentId);
}
