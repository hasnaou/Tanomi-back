package net.hsn.taskschedulerandeventmanagementtoolapi.controllers;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.TaskDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Task;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TPriority;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.TState;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.TaskRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tasks")
public class TaskController {
    private final ITaskService iTaskService;
    private final TaskRepository taskRepository;
    private final ModelMapper mapper;

    public TaskController(ITaskService iTaskService, TaskRepository taskRepository, ModelMapper mapper) {
        this.iTaskService = iTaskService;
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO, @RequestHeader("User-ID") Long userId) {
        TaskDTO taskDTO1 = iTaskService.addTask(taskDTO, userId);
        return new ResponseEntity<>(taskDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<TaskDTO> editTask(@RequestBody TaskDTO taskDTO, @RequestHeader("User-ID") Long userId) {
        TaskDTO taskDTO1 = iTaskService.aditTask(taskDTO, userId);
        return new ResponseEntity<>(taskDTO1, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteEvent(@PathVariable Long id) {
        TaskDTO taskDTO = iTaskService.showTaskById(id);
        Map<String, Boolean> response = new HashMap<>();
        if(iTaskService.deleteTask(taskDTO)){
            response.put("deleted", Boolean.TRUE);
        }
        return response;
    }

    @GetMapping("/task/{taskId}")
    public TaskDTO showEventById(@PathVariable Long taskId) {
        return iTaskService.showTaskById(taskId);
    }

    @GetMapping
    public List<TaskDTO> showAllEvents() {
        return iTaskService.showAllTasks();
    }

    @GetMapping("/user/task/")
    public List<TaskDTO> showTasksByUserId(@RequestHeader("User-ID") Long userId) {
        return iTaskService.showTasksByUserId(userId);
    }

    @GetMapping("/user/task/{username}")
    public List<TaskDTO> showTasksByUsername(@PathVariable String username) {
        return iTaskService.showTasksByUsername(username);
    }

    @PutMapping("/edit/{id}/priority")
    public ResponseEntity<TaskDTO> updateTaskPriority(@PathVariable Long id, @RequestBody TPriority newPriority) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setPriority(newPriority);
            Task updatedTask = taskRepository.save(task);
            TaskDTO taskDTO = mapper.map(updatedTask, TaskDTO.class);
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{id}/state/{newState}")
    public ResponseEntity<TaskDTO> updateTaskState(@PathVariable Long id, @PathVariable TState newState) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setState(newState);
            Task updatedTask = taskRepository.save(task);
            TaskDTO taskDTO = mapper.map(updatedTask, TaskDTO.class);
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/project/task/{id}")
    public List<TaskDTO> showTasksByProject(@PathVariable Long id) {
        return iTaskService.showTasksByProject(id);
    }

    @GetMapping("/taskDone/{id}")
    public List<TaskDTO> showTaskByStateDone(@PathVariable Long id, @RequestHeader("User-ID") Long userId) {
        return iTaskService.showTaskByStateDone(id, userId);
    }

    @GetMapping("/taskInProgress/{id}")
    public List<TaskDTO> showTaskByStateInProgress(@PathVariable Long id, @RequestHeader("User-ID") Long userId) {
        return iTaskService.showTaskByStateInProgress(id, userId);
    }

    @GetMapping("/taskToDo/{id}")
    public List<TaskDTO> showTaskByStateToDo(@PathVariable Long id, @RequestHeader("User-ID") Long userId) {
        return iTaskService.showTaskByStateToDo(id, userId);
    }
}
