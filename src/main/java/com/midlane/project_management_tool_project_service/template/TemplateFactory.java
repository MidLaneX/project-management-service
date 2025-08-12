package com.midlane.project_management_tool_project_service.template;

import org.springframework.stereotype.Component;

import java.util.*;

@Component

public class TemplateFactory {

    private final Map<String, Template> templateMap;

    public TemplateFactory(List<Template> templates) {
        this.templateMap = new HashMap<>();
        for (Template template : templates) {
            this.templateMap.put(template.getTemplateType().toLowerCase(), template);
        }
    }

    public Template getTemplate(String type) {
        Template template = templateMap.get(type.toLowerCase());
        if (template == null) {
            throw new IllegalArgumentException("Unknown template type: " + type);
        }
        return template;
    }
}