package com.midlane.project_management_tool_project_service.service.TaskExecuter;

import java.util.Map;

public interface TaskHandler {
    void execute(Long projectId, Map<String, Object> data);
}
