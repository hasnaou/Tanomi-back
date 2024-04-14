package net.hsn.taskschedulerandeventmanagementtoolapi.services;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.TaskDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Project;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;

import java.util.List;

public interface ITaskService {
    TaskDTO addTask(TaskDTO taskDTO, Long userId);
    TaskDTO aditTask(TaskDTO taskDTO, Long userId);
    boolean deleteTask(TaskDTO taskDTO);
    TaskDTO showTaskById(Long id);
    List<TaskDTO> showTasksByUserId(Long userId);
    List<TaskDTO> showTasksByUsername(String username);
    List<TaskDTO> showTasksByProject(Long projectId);
    List<TaskDTO> showAllTasks();
    TaskDTO updateTaskPriority(Long taskId,  TPriority priority);
    TaskDTO updateTaskState(Long taskId, TState newState);
    List<TaskDTO> showTaskByStateToDo(Long projectId, Long userId);
    List<TaskDTO> showTaskByStateInProgress(Long projectId, Long userId);
    List<TaskDTO> showTaskByStateDone(Long projectId, Long userId);

}
