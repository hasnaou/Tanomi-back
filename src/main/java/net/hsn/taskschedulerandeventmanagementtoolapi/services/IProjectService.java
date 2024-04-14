package net.hsn.taskschedulerandeventmanagementtoolapi.services;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.ProjectDTO;

import java.util.List;

public interface IProjectService {
    ProjectDTO addProject(ProjectDTO projectDTO, Long userId);
    ProjectDTO editProject(ProjectDTO projectDTO, Long userdId);
    boolean deleteProject(ProjectDTO projectDTO);
    ProjectDTO showProjectById(Long id);
    List<ProjectDTO> showProjectsByUserId(Long userId);
    List<ProjectDTO> showProjectsByUsername(String username);
    List<ProjectDTO> showAllProjects();
    List<ProjectDTO> showProjectsWhereAllTasksAreDone();
    List<ProjectDTO> showProjectsWhereAllTasksAreInprogress();

    ProjectDTO addOnlyProject(ProjectDTO projectDTO, Long userId);
}
