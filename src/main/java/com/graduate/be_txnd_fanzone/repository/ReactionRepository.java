package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Reaction;
import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    long countByPost_PostId(Long postId);

    boolean existsByPost_PostIdAndUser_UserId(Long postId, Long userId);

    Optional<Reaction> findByPost_PostIdAndUser_UserId(Long postId, Long userId);

    List<Reaction> findByPost_PostId(Long postId);

    @Query("SELECT r.post.postId, COUNT(r) " +
            "FROM Reaction r " +
            "WHERE r.post.postId IN :postIds " +
            "GROUP BY r.post.postId")
    List<Object[]> countReactionsForPostIds(@Param("postIds") List<Long> postIds);

    @Query("SELECT r.post.postId " +
            "FROM Reaction r " +
            "WHERE r.post.postId IN :postIds " +
            "AND r.user.userId = :userId")
    Set<Long> findPostIdsLikedByUser(@Param("postIds") List<Long> postIds, @Param("userId") Long userId);

    List<Long> user(User user);
}
