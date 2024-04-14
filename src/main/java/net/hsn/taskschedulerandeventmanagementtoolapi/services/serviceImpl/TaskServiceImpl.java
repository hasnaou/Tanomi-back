package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.TaskDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.ProjectRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.TaskRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper mapper, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Task task = mapper.map(taskDTO, Task.class);
        if (task.getProject() != null && task.getProject().getId() != null) {
            Project project = projectRepository.findById(task.getProject().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: "));
            task.setProject(project);
        }
        task.setTimeStart(LocalDateTime.now());
        task.setUser(user);
        task.setDeleted(Boolean.FALSE);
        task.setState(TState.TO_DO);
        task = taskRepository.save(task);
        return mapper.map(task, TaskDTO.class);
    }

    @Override
    public TaskDTO aditTask(TaskDTO taskDTO, Long userId) {
        Task task = taskRepository.save(mapper.map(taskDTO, Task.class));
        return mapper.map(task, TaskDTO.class);
    }

    @Override
    public boolean deleteTask(TaskDTO taskDTO) {
        taskDTO.setDeleted(Boolean.TRUE);
        Task task = taskRepository.save(mapper.map(taskDTO, Task.class));
        return task.isDeleted();
    }

    @Override
    public TaskDTO showTaskById(Long id) {
        Task task = taskRepository.findById(id).get();
        return mapper.map(task, TaskDTO.class);
    }

    @Override
    public List<TaskDTO> showTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> showTasksByUsername(String username) {
        List<Task> tasks = taskRepository.findTaskByUsername(username);
        return tasks.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> showTasksByProject(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskDTO> showAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTaskPriority(Long taskId, TPriority priority) {
        Task task = taskRepository.findById(taskId).get();
        task.setPriority(priority);
        Task updatedTask = taskRepository.save(task);
        return mapper.map(updatedTask, TaskDTO.class);
    }

    @Override
    public TaskDTO updateTaskState(Long taskId, TState newState) {
        Task task = taskRepository.findById(taskId).get();
        task.setState(newState);
        Task updatedTask = taskRepository.save(task);
        return mapper.map(updatedTask, TaskDTO.class);
    }

    @Override
    public List<TaskDTO> showTaskByStateToDo(Long projectId, Long userId) {
        List<Task> taskList = taskRepository.findTaskByState(projectId, userId, TState.TO_DO);
        return taskList.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> showTaskByStateDone(Long projectId, Long userId) {
        List<Task> taskList = taskRepository.findTaskByState(projectId, userId, TState.DONE);
        return taskList.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> showTaskByStateInProgress(Long projectId, Long userId) {
        List<Task> taskList = taskRepository.findTaskByState(projectId, userId, TState.IN_PROGRESS);
        return taskList.stream()
                .filter(task -> !task.isDeleted())
                .map(t -> mapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

}
