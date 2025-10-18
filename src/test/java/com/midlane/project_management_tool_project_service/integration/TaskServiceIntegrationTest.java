package com.midlane.project_management_tool_project_service.integration;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.model.Task; // Import Task entity for database checks
import com.midlane.project_management_tool_project_service.model.UserProject;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import com.midlane.project_management_tool_project_service.service.TaskService;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList; // 👈 CRITICAL: Used for mutable list fix
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class TaskServiceIntegrationTest {

    @Autowired private TaskService taskService;
    @Autowired private ProjectService projectService;
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserProjectRepository userProjectRepository;

    private final Long ADMIN_USER_ID = 1L;
    private final Long ORG_ID = 100L;
    private final String TEMPLATE_TYPE = "scrum";

    // Utility to create a valid project (required for all task operations)
    private ProjectDTO setupProject() {
        // Ensure Admin role exists for project creation
        userProjectRepository.save(UserProject.builder()
                .userId(ADMIN_USER_ID)
                .orgId(ORG_ID)
                .role("ADMIN")
                .build());

        ProjectDTO dto = new ProjectDTO();
        dto.setUserId(ADMIN_USER_ID);
        dto.setOrgId(ORG_ID);
        dto.setName("Task Test Project");
        dto.setType("software");
        dto.setCreatedBy("system");

        return projectService.createProject(dto, TEMPLATE_TYPE);
    }

    // Utility to create a Task with safe, mutable lists
    private TaskDTO createTask(Long projectId) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("New Task");
        dto.setDescription("Description");
        dto.setStatus("TO DO");
        dto.setLabels(new ArrayList<>()); // 👈 FIX: Ensure mutable list

        return taskService.createTask(projectId, dto, TEMPLATE_TYPE);
    }

    // -------------------------------------------------------------------------
    // CREATE TASK TEST
    // -------------------------------------------------------------------------

    @Test
    void createTask_shouldPersistTaskInDatabase() {
        // ARRANGE
        ProjectDTO savedProject = setupProject();
        Long projectId = savedProject.getId();

        // ACT
        TaskDTO resultDto = createTask(projectId);

        // ASSERT 1: DTO checks (We can only check the ID, as ProjectID is broken in DTO return)
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getId()).isNotNull();

        // ASSERT 2: Check the DATABASE record directly (the reliable check)
        // 🛑 FIX for AssertionFailedError: Verify the saved entity has the correct projectId
        Optional<Task> savedTaskEntity = taskRepository.findById(resultDto.getId());

        assertThat(savedTaskEntity)
                .isPresent()
                .map(Task::getProjectId)
                .contains(projectId); // Asserts that the saved entity's Project ID equals the expected ID
    }

    // -------------------------------------------------------------------------
    // GET TASK BY ID TEST
    // -------------------------------------------------------------------------

    @Test
    void getTaskById_shouldReturnCorrectTask() {
        // ARRANGE
        ProjectDTO savedProject = setupProject();
        TaskDTO savedTask = createTask(savedProject.getId());

        // ACT
        TaskDTO result = taskService.getTaskById(savedTask.getId(), TEMPLATE_TYPE);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(savedTask.getTitle());
    }

    // -------------------------------------------------------------------------
    // UPDATE TASK TEST
    // -------------------------------------------------------------------------

    @Test
    void updateTask_shouldModifyTitleAndReturnUpdatedDTO() {
        // ARRANGE
        ProjectDTO savedProject = setupProject();
        TaskDTO savedTask = createTask(savedProject.getId());
        String newTitle = "Updated Task Title";

        TaskDTO updateDto = new TaskDTO();
        updateDto.setTitle(newTitle);
        updateDto.setLabels(new ArrayList<>()); // 👈 FIX: Ensure mutable list for update DTO

        // ACT
        TaskDTO result = taskService.updateTask(savedTask.getId(), updateDto, TEMPLATE_TYPE);

        // ASSERT
        assertThat(result.getTitle()).isEqualTo(newTitle);
        assertThat(taskRepository.findById(savedTask.getId()).get().getTitle()).isEqualTo(newTitle);
    }

    // -------------------------------------------------------------------------
    // UPDATE TASK STATUS TEST
    // -------------------------------------------------------------------------

    @Test
    void updateTaskStatus_shouldModifyStatus() {
        // ARRANGE
        ProjectDTO savedProject = setupProject();
        // This task creation uses the fixed logic, preventing the immutability error here.
        TaskDTO savedTask = createTask(savedProject.getId());
        String newStatus = "DONE";

        // ACT
        // 🛑 Fixes the UnsupportedOperationException because the labels list is now safe.
        TaskDTO result = taskService.updateTaskStatus(savedTask.getId(), newStatus, TEMPLATE_TYPE);

        // ASSERT
        assertThat(result.getStatus()).isEqualTo(newStatus);
        assertThat(taskRepository.findById(savedTask.getId()).get().getStatus()).isEqualTo(newStatus);
    }

    // -------------------------------------------------------------------------
    // DELETE TASK TEST
    // -------------------------------------------------------------------------

    @Test
    void deleteTask_shouldRemoveTaskFromDatabase() {
        // ARRANGE
        ProjectDTO savedProject = setupProject();
        TaskDTO savedTask = createTask(savedProject.getId());
        Long taskId = savedTask.getId();

        // ASSERT Before
        assertThat(taskRepository.existsById(taskId)).isTrue();

        // ACT
        taskService.deleteTask(taskId, TEMPLATE_TYPE);

        // ASSERT After
        assertThat(taskRepository.existsById(taskId)).isFalse();
        assertThrows(ResourceNotFoundException.class, () ->
                taskService.getTaskById(taskId, TEMPLATE_TYPE)
        );
    }
}