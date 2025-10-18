package com.midlane.project_management_tool_project_service.integration;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.model.UserProject;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import com.midlane.project_management_tool_project_service.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    // Standard test constants for clarity
    private final Long ADMIN_USER_ID = 1L;
    private final Long MEMBER_USER_ID = 2L;
    private final Long TEAM_ID = 200L;
    private final Long ORG_ID = 100L;
    private final String TEMPLATE_TYPE = "scrum";

    // Utility function to create and persist a project for other tests
    private ProjectDTO createAdminProject() {
        // Setup Admin role (necessary for creation)
        userProjectRepository.save(UserProject.builder()
                .userId(ADMIN_USER_ID)
                .orgId(ORG_ID)
                .role("ADMIN")
                .build());

        ProjectDTO dto = new ProjectDTO();
        dto.setUserId(ADMIN_USER_ID);
        dto.setOrgId(ORG_ID);
        dto.setName("Initial Project Name");
        dto.setType("software");
        dto.setCreatedBy("test@example.com");

        return projectService.createProject(dto, TEMPLATE_TYPE);
    }



    @Test
    void createProject_shouldPersistProjectInDatabase() {
        // ARRANGE: Setup Admin role
        userProjectRepository.save(UserProject.builder()
                .userId(ADMIN_USER_ID)
                .orgId(ORG_ID)
                .role("ADMIN")
                .build());

        ProjectDTO dto = new ProjectDTO();
        dto.setUserId(ADMIN_USER_ID);
        dto.setOrgId(ORG_ID);
        dto.setName("Integration Test Project");
        dto.setType("software");
        dto.setCreatedBy("rashmika@gmail.com");

        // ACT
        ProjectDTO result = projectService.createProject(dto, TEMPLATE_TYPE);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(projectRepository.findById(result.getId())).isPresent();
    }



    @Test
    void updateProject_shouldFailForNonAdminUser() {
        // ARRANGE: Create an initial project
        ProjectDTO initialProject = createAdminProject();
        Long projectId = initialProject.getId();

        // Ensure a non-admin user exists in the system (no ADMIN role saved for MEMBER_USER_ID)
        ProjectDTO updateDto = new ProjectDTO();
        updateDto.setName("Attempted Change");

        // ACT & ASSERT: Attempt to update as a non-admin should throw a SecurityException
        assertThrows(SecurityException.class, () -> {
            projectService.updateProject(MEMBER_USER_ID, projectId, TEMPLATE_TYPE, updateDto);
        }, "Only ADMIN can update this project");
    }



    @Test
    void deleteProject_shouldRemoveProjectFromDatabase() {
        // ARRANGE: Create an initial project
        ProjectDTO initialProject = createAdminProject();
        Long projectId = initialProject.getId();

        // ASSERT 1: Verify project exists before deletion
        assertThat(projectRepository.existsById(projectId)).isTrue();

        // ACT: Delete the project as the Admin user
        projectService.deleteProject(ADMIN_USER_ID, projectId, TEMPLATE_TYPE);

        // ASSERT 2: Verify the project is no longer in the database
        assertThat(projectRepository.existsById(projectId)).isFalse();
    }

    @Test
    void deleteProject_shouldFailForNonAdminUser() {
        // ARRANGE: Create an initial project
        ProjectDTO initialProject = createAdminProject();
        Long projectId = initialProject.getId();

        // ACT & ASSERT: Attempt to delete as a non-admin should throw a SecurityException
        assertThrows(SecurityException.class, () -> {
            projectService.deleteProject(MEMBER_USER_ID, projectId, TEMPLATE_TYPE);
        }, "Only ADMIN can delete this project");

        // ASSERT: Ensure the project was NOT deleted
        assertThat(projectRepository.existsById(projectId)).isTrue();
    }



}