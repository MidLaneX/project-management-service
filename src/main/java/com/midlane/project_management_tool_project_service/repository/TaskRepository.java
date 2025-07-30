package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task, Long> {}