package com.midlane.project_management_tool_project_service.service.TaskExecuter;

import com.midlane.project_management_tool_project_service.model.Feature;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.repository.FeatureRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateTaskHandler implements TaskHandler {

    private final TaskRepository taskRepository;
    private final FeatureRepository featureRepository;

    public CreateTaskHandler(TaskRepository taskRepository, FeatureRepository featureRepository) {
        this.taskRepository = taskRepository;
        this.featureRepository = featureRepository;
    }

    @Override
    public void execute(Long projectId, Map<String, Object> data) {
        String name = (String) data.get("name");
        Long featureId = Long.valueOf(data.get("featureId").toString());

        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new RuntimeException("Feature not found"));

        Task task = new Task();
        task.setName(name);
        task.setFeature(feature);
        taskRepository.save(task);
    }
}

