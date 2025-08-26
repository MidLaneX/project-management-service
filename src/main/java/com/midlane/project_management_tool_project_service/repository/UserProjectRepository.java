package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
 List<UserProject> findByProjectId(Long projectId);
 List<UserProject> findByUserId(Long userId);
 @Query("SELECT DISTINCT p FROM Project p JOIN UserProject up ON p.id = up.projectId WHERE up.teamId IN :teamIds")
 List<Project> findProjectsByTeamIds(@Param("teamIds") List<Long> teamIds);

}

