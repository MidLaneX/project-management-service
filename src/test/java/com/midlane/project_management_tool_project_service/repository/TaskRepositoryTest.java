package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

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
