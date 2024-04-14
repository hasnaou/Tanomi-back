package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;

import java.time.LocalDateTime;

@Data
public class ReminderDTO {
    private Long id;
    private LocalDateTime reminderDate;
    private Task task;
    private Event event;
    private User user;
}
