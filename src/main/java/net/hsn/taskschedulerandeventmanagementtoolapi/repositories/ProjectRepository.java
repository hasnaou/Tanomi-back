package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN p.user u WHERE u.username = :username")
    List<Project> findProjectByUsername(@Param("username") String username);
    List<Project> findByUserId(Long userId);
    @Query("SELECT p FROM Project p WHERE NOT EXISTS (SELECT t FROM Task t WHERE t.project = p AND t.state != :state)")
    List<Project> findProjectsWhereAllTasks(TState state);
}
