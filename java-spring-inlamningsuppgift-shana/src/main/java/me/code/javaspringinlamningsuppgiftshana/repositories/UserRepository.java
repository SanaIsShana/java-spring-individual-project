package me.code.javaspringinlamningsuppgiftshana.repositories;

import me.code.javaspringinlamningsuppgiftshana.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository manages user data
 * and there is a custom method to find user by name.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
