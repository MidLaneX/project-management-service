package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.TeamProject;
import com.midlane.project_management_tool_project_service.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {

 // Find all TeamProject entries by teamId
 List<TeamProject> findByTeamId(Long teamId);

 // Find all TeamProject entries for a list of teamIds
 List<TeamProject> findByTeamIdIn(List<Long> teamIds);

 // Custom query: fetch Projects directly by teamIds
 @Query("SELECT DISTINCT tp.project FROM TeamProject tp WHERE tp.teamId IN :teamIds")
 List<Project> findProjectsByTeamIds(@Param("teamIds") List<Long> teamIds);

 // Optional: fetch all projects for a specific team and organization (if needed)
 @Query("SELECT DISTINCT tp.project FROM TeamProject tp JOIN tp.project p WHERE tp.teamId IN :teamIds AND p.orgId = :orgId")
 List<Project> findProjectsByTeamIdsAndOrgId(@Param("teamIds") List<Long> teamIds, @Param("orgId") Long orgId);
}

