package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    List<Task> findTaskByUsername(@Param("username") String username);
    List<Task> findByUserId(Long userId);
    List<Task> findByProjectId(Long projectId);
    @Query("SELECT t FROM Task t WHERE t.state = :state AND t.project.id = :projectId AND t.user.id = :userId")
    List<Task> findTaskByState(@Param("projectId") Long projectId, @Param("userId") Long userId, @Param("state") TState state);
}
