package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.EventDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.ProjectDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Event;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.EventRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    ProjectRepository projectRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    TaskRepository taskRepository;
    ModelMapper modelMapper;
    @InjectMocks
    ProjectServiceImpl projectServiceImpl;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        projectServiceImpl = new ProjectServiceImpl(projectRepository,modelMapper,userRepository, taskRepository);
    }

    @Test
    void addProject() {
        User user = new User(1L);
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(1L)
                .title("project test")
                .description("project description")
                .deleted(false)
                .tasks(new ArrayList<>())
                .user(user)
                .build();
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectServiceImpl.addProject(projectDTO, 1L);

        assertEquals(projectDTO, result);
    }

    @Test
    void editProject() {
        User user = new User(1L);
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(1L)
                .title("project test")
                .description("project description")
                .tasks(new ArrayList<>())
                .deleted(false)
                .user(user)
                .build();
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(false);
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectServiceImpl.editProject(projectDTO,1L);

        verify(projectRepository, times(1)).save(any(Project.class));

        assertNotNull(result);
        assertEquals(project.getId(), result.getId());
        assertEquals(project.getTitle(), result.getTitle());
    }

    @Test
    void deleteProject() {
        User user = new User(1L);
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(1L)
                .title("project test")
                .description("project description")
                .tasks(new ArrayList<>())
                .deleted(false)
                .user(user)
                .build();
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(true);
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        boolean result = projectServiceImpl.deleteProject(projectDTO);

        verify(projectRepository, times(1)).save(any(Project.class));
        assertTrue(result);
    }

    @Test
    void showProjectById() {
        User user = new User(1L);
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(false);
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectDTO result = projectServiceImpl.showProjectById(1L);

        verify(projectRepository, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals(project.getId(), result.getId());
        assertEquals(project.getTitle(), result.getTitle());
    }

    @Test
    void showProjectsByUserId() {
        User user = new User(1L);
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(false);
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(projectRepository.findByUserId(1L)).thenReturn(Collections.singletonList(project));

        List<ProjectDTO> results = projectServiceImpl.showProjectsByUserId(1L);

        verify(projectRepository, times(1)).findByUserId(1L);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showProjectsByUsername() {
        User user = new User(1L);
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(false);
        project.setTasks(new ArrayList<>());
        project.setUser(user);

        when(projectRepository.findProjectByUsername("hasna")).thenReturn(Collections.singletonList(project));

        List<ProjectDTO> results = projectServiceImpl.showProjectsByUsername("hasna");

        verify(projectRepository, times(1)).findProjectByUsername("hasna");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void showAllProjects() {
        List<Project> mockprojects = new ArrayList<>();
        User user = new User(1L,"hasna");
        Project project = new Project();
        project.setId(1L);
        project.setTitle("project test");
        project.setDescription("project description");
        project.setDeleted(false);
        project.setTasks(new ArrayList<>());
        project.setUser(user);
        mockprojects.add(project);mockprojects.add(project);

        when(projectRepository.findAll()).thenReturn(mockprojects);

        List<ProjectDTO> result = projectServiceImpl.showAllProjects();

        assertEquals(2, result.size());
    }
}