package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.TeamProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {
    @Query("SELECT tp.project FROM TeamProject tp WHERE tp.teamId IN :teamIds")
    List<Project> findProjectsByTeamIds(@Param("teamIds") List<Long> teamIds);
}