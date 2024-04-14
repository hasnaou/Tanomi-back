//package net.hsn.taskschedulerandeventmanagementtoolapi.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.hsn.taskschedulerandeventmanagementtoolapi.config.TestConfig;
//import net.hsn.taskschedulerandeventmanagementtoolapi.dtos.ProjectDTO;
//import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
//import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.ProjectRepository;
//import net.hsn.taskschedulerandeventmanagementtoolapi.security.jwt.JwtUtils;
//import net.hsn.taskschedulerandeventmanagementtoolapi.security.services.UserDetailsServiceImpl;
//import net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl.ProjectServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.security.web.csrf.DefaultCsrfToken;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(ProjectController.class)
//class ProjectControllerTest {
//
//    @MockBean
//    ProjectServiceImpl projectServiceImpl;
//    @MockBean
//    ProjectRepository projectRepository;
//    @MockBean
//    JwtUtils jwtUtils;
//    @MockBean
//    UserDetailsServiceImpl userDetailsService;
//    @MockBean
//    ModelMapper modelMapper;
//    @Autowired
//    MockMvc mockMvc;
//    TestConfig testConfig = new TestConfig();
//    ObjectMapper objectMapper = testConfig.objectMapper();
//
//    @Test
//    void addProject() throws Exception {
//        User user = new User(1L);
//        ProjectDTO projectDTO = ProjectDTO.builder()
//                .id(1L)
//                .title("project test")
//                .description("project description")
//                .tasks(new ArrayList<>())
//                .deleted(false)
//                .user(user)
//                .build();
//
//        when(projectServiceImpl.addProject(any(ProjectDTO.class),eq(1L))).thenReturn(projectDTO);
//
//        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "CSRF-TOKEN-VALUE");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("X-CSRF-TOKEN", csrfToken.getToken())
//                        .content(objectMapper.writeValueAsString(projectDTO)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void editProject() {
//    }
//
//    @Test
//    void deleteProject() {
//    }
//
//    @Test
//    void showProjectById() {
//    }
//
//    @Test
//    void showAllProjects() {
//    }
//
//    @Test
//    void showProjectsByUserId() {
//    }
//}