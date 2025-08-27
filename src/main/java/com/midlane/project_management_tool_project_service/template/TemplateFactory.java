package com.midlane.project_management_tool_project_service.template;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TemplateFactory {

    private final Map<String, Template> templateMap;

    public TemplateFactory(List<Template> templates) {
        this.templateMap = templates.stream()
                .collect(Collectors.toMap(
                        t -> t.getTemplateType().toLowerCase(),
                        t -> t
                ));
    }

    public Template getTemplate(String type) {
        Template template = templateMap.get(type.toLowerCase());
        if (template == null) {
            throw new IllegalArgumentException("Unknown template type: " + type);
        }
        return template;
    }
}
