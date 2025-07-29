package com.midlane.project_management_tool_project_service.strategy;

public class StrategyFactory {

    public static TemplateStrategy getStrategy(String templateName) {
        switch (templateName.toLowerCase()) {
            case "scrum":
                return new ScrumStrategy();
            // Add other templates here
            default:
                throw new IllegalArgumentException("Unsupported template: " + templateName);
        }
    }
}
