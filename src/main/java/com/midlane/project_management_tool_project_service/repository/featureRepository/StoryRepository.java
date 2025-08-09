package com.midlane.project_management_tool_project_service.repository.featureRepository;



import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByProjectId(Long projectId);
    List<Story> findBySprintId(Long sprintId);
    List<Story> findByProjectIdAndSprintIdIsNull(Long projectId);
}
