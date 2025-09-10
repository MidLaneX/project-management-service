package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findByUserIdAndOrgId(Long userId, Long orgId);

    List<UserProject> findByUserIdAndOrgIdAndRole(Long userId, Long orgId, String role);
}
