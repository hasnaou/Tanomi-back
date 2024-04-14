package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;
import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;

@Entity
@Data
@Table(name = "priorities")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TPriority name;
}