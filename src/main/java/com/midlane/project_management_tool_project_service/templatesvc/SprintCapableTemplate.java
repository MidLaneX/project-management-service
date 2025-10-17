package com.midlane.project_management_tool_project_service.templatesvc;

import com.midlane.project_management_tool_project_service.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public interface SprintCapableTemplate extends Template {
    SprintDTO createSprint(Long projectId, SprintDTO sprintDTO);
    SprintDTO getSprint(Long projectId);
    List<SprintDTO> getAllSprint(Long projectId);
    SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO);
    void deleteSprint(Long projectId, Long sprintId);

    @Setter
    @Getter
    class FeatureDescriptor {
        private String key;
        private String displayName;

        public FeatureDescriptor(String key, String displayName) {
            this.key = key;
            this.displayName = displayName;
        }

    }
}
