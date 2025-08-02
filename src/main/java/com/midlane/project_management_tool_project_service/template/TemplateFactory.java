// TemplateFactory.java
package com.midlane.project_management_tool_project_service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemplateFactory {

    private final ScrumTemplateImpl scrumTemplate;

    public Template getTemplate(String type) {
        if ("scrum".equalsIgnoreCase(type)) {
            return scrumTemplate;
        }
        throw new IllegalArgumentException("Unknown template type: " + type);
    }

    public ScrumTemplate getScrumTemplate() {
        return scrumTemplate;
    }
}
