package com.boram.look.domain.user.repository;

import com.boram.look.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

}
