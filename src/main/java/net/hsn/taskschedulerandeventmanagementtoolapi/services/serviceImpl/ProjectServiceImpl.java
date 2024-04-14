package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.ProjectDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.ProjectRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.TaskRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.IProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper mapper, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ProjectDTO addProject(ProjectDTO projectDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Project project = mapper.map(projectDTO, Project.class);
        project.setUser(user);
        project.setDeleted(Boolean.FALSE);
        project = projectRepository.save(project);
        final Project finalProject = project;
        List<Task> tasks = projectDTO.getTasks().stream()
                .map(taskDTO -> {
                    Task task = mapper.map(taskDTO, Task.class);
                    task.setTimeStart(LocalDateTime.now());
                    task.setState(TState.TO_DO);
                    task.setProject(finalProject);
                    return task;
                })
                .collect(Collectors.toList());
        tasks = taskRepository.saveAll(tasks);
        project.setTasks(tasks);
        return mapper.map(project, ProjectDTO.class);
    }


    @Override
    public ProjectDTO addOnlyProject(ProjectDTO projectDTO, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Project project = projectRepository.save(mapper.map(projectDTO, Project.class));
        project.setUser(user);
        project.setDeleted(Boolean.FALSE);
        project = projectRepository.save(project);
        return mapper.map(project, ProjectDTO.class);
    }

    @Override
    public ProjectDTO editProject(ProjectDTO projectDTO, @RequestHeader Long userId) {
        Project project = mapper.map(projectDTO, Project.class);
        project.setDeleted(Boolean.FALSE);
        project = projectRepository.save(project);
        return mapper.map(project, ProjectDTO.class);
    }

    @Override
    public boolean deleteProject(ProjectDTO projectDTO) {
        projectDTO.setDeleted(Boolean.TRUE);
        Project project = projectRepository.save(mapper.map(projectDTO, Project.class));
        return project.isDeleted();
    }

    @Override
    public ProjectDTO showProjectById(Long id) {
        Project project = projectRepository.findById(id).get();
        return mapper.map(project, ProjectDTO.class);
    }

    @Override
    public List<ProjectDTO> showProjectsByUserId(@RequestHeader Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        return projects.stream()
                .filter(project -> !project.isDeleted())
                .map(p -> mapper.map(p, ProjectDTO.class))
                .toList();
    }

    @Override
    public List<ProjectDTO> showProjectsByUsername(String username) {
        List<Project> projects = projectRepository.findProjectByUsername(username);
        return projects.stream()
                .filter(project -> !project.isDeleted())
                .map(p -> mapper.map(p, ProjectDTO.class))
                .toList();
    }

    @Override
    public List<ProjectDTO> showAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .filter(project -> !project.isDeleted())
                .map(e -> mapper.map(e, ProjectDTO.class))
                .toList();
    }

    @Override
    public List<ProjectDTO> showProjectsWhereAllTasksAreDone() {
        List<Project> projectList = projectRepository.findProjectsWhereAllTasks(TState.DONE);
        return projectList.stream()
                .filter(project -> !project.isDeleted())
                .map(e -> mapper.map(e, ProjectDTO.class))
                .toList();
    }

    @Override
    public List<ProjectDTO> showProjectsWhereAllTasksAreInprogress() {
        List<Project> projectList = projectRepository.findProjectsWhereAllTasks(TState.IN_PROGRESS);
        return projectList.stream()
                .filter(project -> !project.isDeleted())
                .map(e -> mapper.map(e, ProjectDTO.class))
                .toList();
    }

}
