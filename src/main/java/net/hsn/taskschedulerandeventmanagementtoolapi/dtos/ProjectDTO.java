package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean deleted;
    private List<Task> tasks;
    private User user;
}
