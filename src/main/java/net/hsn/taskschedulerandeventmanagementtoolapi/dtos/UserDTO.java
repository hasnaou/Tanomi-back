package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.URole;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private URole role;
    private List<Event> events;
    private List<Task> tasks;
}
