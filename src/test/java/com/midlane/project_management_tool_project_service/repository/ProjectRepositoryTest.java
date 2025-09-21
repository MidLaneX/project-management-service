package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findByOrgId_ShouldReturnProjects() {
        Project project1 = Project.builder()
                .name("Project 1")
                .type("Scrum")
                .templateType("Default")
                .orgId(1L)
                .build();

        Project project2 = Project.builder()
                .name("Project 2")
                .type("Kanban")
                .templateType("Default")
                .orgId(1L)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project2);

        List<Project> result = projectRepository.findByOrgId(1L);
        assertThat(result).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("Project 1", "Project 2");
    }
}
