package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reminderDate;
    @ManyToOne
    private Task task;
    @ManyToOne
    private Event event;
    @ManyToOne
    private User user;
}
