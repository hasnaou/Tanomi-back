package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

}
