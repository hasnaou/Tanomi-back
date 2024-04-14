package net.hsn.taskschedulerandeventmanagementtoolapi.repositories;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventByEventDate(LocalDate eventDate);
    Event findEventByDeletedFalse();
    @Query("SELECT e FROM Event e JOIN e.user u WHERE u.username = :username")
    List<Event> findEventByUsername(@Param("username") String username);
    List<Event> findByUserId(Long userId);
}
