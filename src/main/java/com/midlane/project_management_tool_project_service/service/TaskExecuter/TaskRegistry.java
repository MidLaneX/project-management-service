package com.midlane.project_management_tool_project_service.service.TaskExecuter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskRegistry {
    private final Map<String, TaskHandler> handlers = new HashMap<>();

    public TaskRegistry(List<TaskHandler> handlerList) {
        for (TaskHandler handler : handlerList) {
            handlers.put(handler.getClass().getSimpleName().replace("Handler", "").replaceAll("([A-Z])", " $1").trim(), handler);
        }
    }

    public TaskHandler getHandler(String taskName) {
        return handlers.get(taskName);
    }
}
