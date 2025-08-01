package com.midlane.project_management_tool_project_service.service;

import com.midlane.project_management_tool_project_service.model.Project;

public interface ProjectService {
    Project createProject(String projectName,String templateName);


}