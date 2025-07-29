package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}