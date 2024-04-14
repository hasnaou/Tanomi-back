package net.hsn.taskschedulerandeventmanagementtoolapi.controllers;

import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.ProjectDTO;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/projects")
public class ProjectController {
    public final IProjectService iProjectService;
    public ProjectController(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO, @RequestHeader("User-ID") Long userId) {
        ProjectDTO projectDTO1 = iProjectService.addProject(projectDTO, userId);
        return new ResponseEntity<>(projectDTO1, HttpStatus.CREATED);
    }

    @PostMapping("/projectadd")
    public ResponseEntity<ProjectDTO> addOnlyProject(@RequestBody ProjectDTO projectDTO, @RequestHeader("User-ID") Long userId) {
        ProjectDTO projectDTO1 = iProjectService.addOnlyProject(projectDTO, userId);
        return new ResponseEntity<>(projectDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProjectDTO> editProject(@RequestBody ProjectDTO projectDTO, @RequestHeader("User-ID") Long userId) {
        ProjectDTO projectDTO1 = iProjectService.editProject(projectDTO, userId);
        return new ResponseEntity<>(projectDTO1, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteProject(@PathVariable Long id) {
        ProjectDTO projectDTO = iProjectService.showProjectById(id);
        Map<String, Boolean> response = new HashMap<>();
        if(iProjectService.deleteProject(projectDTO)) {
            response.put("deleted", Boolean.TRUE);
        }
        return response;
    }

    @GetMapping("/project/{id}")
    public ProjectDTO showProjectById(@PathVariable Long id) {
        return iProjectService.showProjectById(id);
    }

    @GetMapping
    public List<ProjectDTO> showAllProjects() {
        return iProjectService.showAllProjects();
    }

    @GetMapping("/user/project")
    public List<ProjectDTO> showProjectsByUserId(@RequestHeader("User-ID") Long userId) {
        return iProjectService.showProjectsByUserId(userId);
    }

    @GetMapping("/taskdone/project")
    public List<ProjectDTO> showProjectsWhereAllTasksAreDone() {
        return iProjectService.showProjectsWhereAllTasksAreDone();
    }

    @GetMapping("/taskinprogress/project")
    public List<ProjectDTO> showProjectsWhereAllTasksAreInprogress() {
        return iProjectService.showProjectsWhereAllTasksAreInprogress();
    }
}
