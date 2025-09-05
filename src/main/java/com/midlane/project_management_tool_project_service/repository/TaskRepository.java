package com.midlane.project_management_tool_project_service.repository;



import com.midlane.project_management_tool_project_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByProjectIdAndStatus(Long projectId, String status);
    List<Task> findBySprintId(Long sprintId);
    List<Task> findByProjectIdAndSprintId(Long projectId, Long sprintId);
    List<Task> findByProjectIdAndStatusNot(Long projectId, String status);

}
