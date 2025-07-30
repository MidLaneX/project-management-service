package com.midlane.project_management_tool_project_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.midlane.project_management_tool_project_service")
@EntityScan("com.midlane.project_management_tool_project_service.model")

public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

}
