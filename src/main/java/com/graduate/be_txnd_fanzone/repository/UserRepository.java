package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndDeleteFlagIsFalse(String username);
}
