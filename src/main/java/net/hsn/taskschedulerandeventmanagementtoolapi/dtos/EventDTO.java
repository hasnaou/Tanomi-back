package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Reminder;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private Boolean deleted;
    private User user;
    private List<Reminder> reminders;
}
