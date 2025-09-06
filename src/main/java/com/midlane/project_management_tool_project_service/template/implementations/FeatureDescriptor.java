package com.midlane.project_management_tool_project_service.template.implementations;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeatureDescriptor {
    private String key;
    private String displayName;

    public FeatureDescriptor(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

}
