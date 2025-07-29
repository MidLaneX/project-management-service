package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByNameIgnoreCase(String name);
}
