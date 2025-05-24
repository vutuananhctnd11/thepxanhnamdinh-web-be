package com.graduate.be_txnd_fanzone.repository;

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
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String email);

    Optional<User> findByUserIdAndDeleteFlagIsFalse(Long userId);

    Optional<User> findByUsernameAndDeleteFlagIsFalse(String username);

    Optional<User> findByEmailAddressAndDeleteFlagIsFalse(String email);

    @Query("""
            SELECT u
            FROM User u
            WHERE
                (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR u.phoneNumber LIKE CONCAT('%', :search, '%')
                OR LOWER(u.emailAddress) LIKE LOWER(CONCAT('%', :search, '%')))
                AND u.userId != :userLoginId
                AND u.deleteFlag=false
            """)
    Page<User> searchUsers(@Param("search") String search, @Param("userLoginId") Long userLoginId, Pageable pageable);

    Page<User> findByRole_IdAndDeleteFlagIsFalseOrderByCreateDateDesc(Integer roleId, Pageable pageable);

    Page<User> findByDeleteFlagIsFalseOrderByCreateDateDesc(Pageable pageable);

}
