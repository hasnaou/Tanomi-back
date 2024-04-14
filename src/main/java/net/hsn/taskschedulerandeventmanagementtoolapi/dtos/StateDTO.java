package net.hsn.taskschedulerandeventmanagementtoolapi.dtos;

import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;

@Data
public class StateDTO {
    private Long id;
    private TState name;
}
