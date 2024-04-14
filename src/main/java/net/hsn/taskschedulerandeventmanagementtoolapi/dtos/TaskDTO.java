package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Reminder;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean deleted;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private User user;
    private List<Reminder> reminders;
    private Project project;
    private TState state;
    private TPriority priority;
}
