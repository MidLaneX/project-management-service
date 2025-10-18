package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserProjectRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("project_service_db")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

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
