package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.TaskDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.ProjectRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.TaskRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ProjectRepository projectRepository;
    ModelMapper modelMapper;
    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        taskServiceImpl = new TaskServiceImpl(taskRepository, modelMapper, userRepository,projectRepository);
    }

    @Test
    void addTask() {
        User user = new User(1L);
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .project(new Project())
                .state(TState.TO_DO)
                .priority(TPriority.NORMAL)
                .build();

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskServiceImpl.addTask(taskDTO, 1L);

        assertEquals(taskDTO, result);
    }

    @Test
    void aditTask() {
        User user = new User(1L);
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .project(new Project())
                .state(TState.TO_DO)
                .priority(TPriority.NORMAL)
                .build();
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskServiceImpl.aditTask(taskDTO,1L);

        verify(taskRepository, times(1)).save(any(Task.class));

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
    }

    @Test
    void deleteTask() {
        User user = new User(1L);
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .deleted(false)
                .user(user)
                .reminders(new ArrayList<>())
                .project(new Project())
                .state(TState.TO_DO)
                .priority(TPriority.NORMAL)
                .build();
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(true);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        boolean result = taskServiceImpl.deleteTask(taskDTO);

        verify(taskRepository, times(1)).save(any(Task.class));
        assertTrue(result);
    }

    @Test
    void showTaskById() {
        User user = new User(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskServiceImpl.showTaskById(1L);

        verify(taskRepository, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
    }

    @Test
    void showTasksByUserId() {
        User user = new User(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(taskRepository.findByUserId(1L)).thenReturn(Collections.singletonList(task));

        List<TaskDTO> results = taskServiceImpl.showTasksByUserId(1L);

        verify(taskRepository, times(1)).findByUserId(1L);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showTasksByUsername() {
        User user = new User(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);

        when(taskRepository.findTaskByUsername("hasna")).thenReturn(Collections.singletonList(task));

        List<TaskDTO> results = taskServiceImpl.showTasksByUsername("hasna");

        verify(taskRepository, times(1)).findTaskByUsername("hasna");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showTasksByProject() {
        User user = new User(1L);
        Project project = new Project(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(project);
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskRepository.findByProjectId(1L)).thenReturn(tasks);

        List<TaskDTO> result = taskServiceImpl.showTasksByProject(1L);

        verify(taskRepository, times(1)).findByProjectId(1L);
        assertEquals(tasks.size(), result.size());
    }

    @Test
    void showAllTasks() {
        User user = new User(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setDeleted(false);
        task.setUser(user);
        task.setReminders(new ArrayList<>());
        task.setProject(new Project());
        task.setState(TState.TO_DO);
        task.setPriority(TPriority.NORMAL);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);tasks.add(task);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskServiceImpl.showAllTasks();

        assertEquals(2, result.size());
    }
}