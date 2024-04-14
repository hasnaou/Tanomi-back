package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;
import lombok.Data;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.URole;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private URole name;
}
