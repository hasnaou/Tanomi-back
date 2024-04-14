package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
