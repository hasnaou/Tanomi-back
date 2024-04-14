package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private Boolean deleted;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Reminder> reminders;

    public boolean isDeleted() {
        return deleted;
    }
}
