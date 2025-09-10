package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import java.util.List;

public interface SprintCapableTemplate extends Template {
    SprintDTO createSprint(Long projectId, SprintDTO sprintDTO);
    SprintDTO getSprint(Long projectId);
    List<SprintDTO> getAllSprint(Long projectId);
    SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO);
    void deleteSprint(Long projectId, Long sprintId);
}
