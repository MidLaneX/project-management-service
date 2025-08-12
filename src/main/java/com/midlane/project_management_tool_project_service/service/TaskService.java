package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.dto.TaskDTO;
import com.midlane.project_management_tool_project_service.template.TemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TemplateFactory templateFactory;

    public TaskDTO createTask(Long projectId, TaskDTO dto, String template) {
        return templateFactory.getTemplate(template).createTask(projectId, dto);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto, String template) {
        return templateFactory.getTemplate(template).updateTask(id, dto);
    }

    public void deleteTask(Long id, String template) {
        templateFactory.getTemplate(template).deleteTask(id);
    }

    public TaskDTO getTaskById(Long id, String template) {
        return templateFactory.getTemplate(template).getTaskById(id);
    }

    public List<TaskDTO> getTasksByProjectId(Long projectId, String template) {
        return templateFactory.getTemplate(template).getTasksByProject(projectId);
    }

    public TaskDTO updateTaskStatus(Long id, String status, String template) {
        return templateFactory.getTemplate(template).updateTaskStatus(id,status);
    }

    public TaskDTO updateSprint(Long id, Long sprintId, String template) {
        return templateFactory.getTemplate(template).updateSprint(id,sprintId);
    }
}
