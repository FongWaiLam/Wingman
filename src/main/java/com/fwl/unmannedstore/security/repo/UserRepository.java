package com.fwl.unmannedstore.security.repo;

import com.fwl.unmannedstore.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

}
