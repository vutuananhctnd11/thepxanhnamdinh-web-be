package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost_PostIdAndDeleteFlagIsFalseOrderByCreateDateDesc(Long postId, Pageable pageable);

    Optional<Comment> findByCommentIdAndDeleteFlagIsFalse(Long commentId);

    long countByPost_PostIdAndDeleteFlagIsFalse(Long postId);

    @Query("SELECT c.post.postId, COUNT(c) " +
            "FROM Comment c " +
            "WHERE c.post.postId IN :postIds " +
            "AND c.deleteFlag = false " +
            "GROUP BY c.post.postId")
    List<Object[]> countCommentsForPostIds(@Param("postIds") List<Long> postIds);

}
