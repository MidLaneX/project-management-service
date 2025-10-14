package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
class TaskRepositoryTest {

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
    private TaskRepository taskRepository;

    @Test
    void findByProjectIdAndStatus_ShouldReturnFilteredTasks() {
        Task task1 = Task.builder().projectId(1L).status("TODO").build();
        Task task2 = Task.builder().projectId(1L).status("DONE").build();
        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> result = taskRepository.findByProjectIdAndStatus(1L, "TODO");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("TODO");
    }
}
