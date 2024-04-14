package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;

@Data
public class PriorityDTO {
    private Long id;
    private TPriority name;
}
