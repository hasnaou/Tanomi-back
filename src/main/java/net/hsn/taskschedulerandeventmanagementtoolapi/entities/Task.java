package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Boolean deleted;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Reminder> reminders;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;
    private TState state;
    private TPriority priority;
    public boolean isDeleted() {
        return deleted;
    }
}
