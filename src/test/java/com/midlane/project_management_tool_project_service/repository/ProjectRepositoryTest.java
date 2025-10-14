package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
class ProjectRepositoryTest {

    // Start a PostgreSQL container for testing
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("project_service_db")
            .withUsername("postgres")
            .withPassword("postgres");

    // Dynamically set Spring datasource properties to use Testcontainers
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findByOrgId_ShouldReturnProjects() {
        // Create test projects
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

        // Save projects to database
        projectRepository.save(project1);
        projectRepository.save(project2);

        // Retrieve projects by orgId
        List<Project> result = projectRepository.findByOrgId(1L);

        // Verify the results
        assertThat(result).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("Project 1", "Project 2");
    }

    @Test
    void saveAndRetrieveProject_ShouldReturnCorrectProject() {
        Project project = Project.builder()
                .name("Test Project")
                .type("Scrum")
                .templateType("Custom")
                .orgId(2L)
                .build();

        projectRepository.save(project);

        List<Project> result = projectRepository.findByOrgId(2L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Project");
    }
}
