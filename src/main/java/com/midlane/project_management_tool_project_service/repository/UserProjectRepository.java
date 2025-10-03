package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findByUserIdAndOrgId(Long userId, Long orgId);

    List<UserProject> findByUserIdAndOrgIdAndRole(Long userId, Long orgId, String role);
    List<UserProject> findByTeamId(Long teamId);
    boolean existsByProjectId(Long projectId);

    @Query("SELECT up.teamId FROM UserProject up WHERE up.projectId = :projectId")
    Long findFirstTeamIdByProjectId(@Param("projectId") Long projectId);

}
