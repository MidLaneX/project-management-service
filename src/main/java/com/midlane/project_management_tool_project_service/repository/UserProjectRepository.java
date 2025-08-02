package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
 List<UserProject> findByProjectId(Long projectId);
}

