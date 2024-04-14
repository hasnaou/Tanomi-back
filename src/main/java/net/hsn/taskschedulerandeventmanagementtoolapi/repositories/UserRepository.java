package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2")
    User findUserByUsernameAndPassword(String username, String password);
}
