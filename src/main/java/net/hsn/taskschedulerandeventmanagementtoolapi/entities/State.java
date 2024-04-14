package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;
import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;

@Entity
@Data
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TState name;
}
