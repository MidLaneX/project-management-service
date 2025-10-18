//package com.midlane.project_management_tool_project_service.integration;
//
//import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
//import com.midlane.project_management_tool_project_service.dto.TaskDTO;
//import com.midlane.project_management_tool_project_service.model.Project;
//import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
//import com.midlane.project_management_tool_project_service.service.ProjectService;
//import com.midlane.project_management_tool_project_service.service.TaskService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//class TaskServiceIntegrationTest {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Test
//    void createTask_shouldPersistInDatabase() {
//
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setName("Integration Project");
//        projectDTO.setType("software");
//        projectDTO.setOrgId(1L);
//        projectDTO.setUserId(1L);
//        projectDTO.setCreatedBy("rashmika");
//
//        ProjectDTO savedProject = projectService.createProject(projectDTO, "scrum");
//
//        assertThat(savedProject).isNotNull();
//        assertThat(savedProject.getId()).isNotNull();
//
//
//        TaskDTO dto = new TaskDTO();
//        dto.setTitle("Integration Task");
//        dto.setDescription("Integration testing task creation");
//
//
//        TaskDTO savedTask = taskService.createTask(savedProject.getId(), dto, "scrum");
//
//
//        assertThat(savedTask).isNotNull();
//        assertThat(savedTask.getId()).isNotNull();
//        assertThat(savedTask.getTitle()).isEqualTo("Integration Task");
//    }
//}
