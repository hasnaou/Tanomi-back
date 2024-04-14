package net.hsn.taskschedulerandeventmanagementtoolapi.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "projects")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean deleted;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Task> tasks;
    public boolean isDeleted() {
        return deleted != null && deleted;
    }
    public Project(Long id){
        this.id = id;
    }
    public Project() {}
}
