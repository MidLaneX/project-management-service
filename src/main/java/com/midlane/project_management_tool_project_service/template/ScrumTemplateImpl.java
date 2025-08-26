package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.SprintRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.StoryRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ScrumTemplateImpl extends AbstractTemplate {


    private static final List<FeatureDescriptor> SCRUM_FEATURES = List.of(
            new FeatureDescriptor("backlog", "Backlog Management"),
            new FeatureDescriptor("sprint", "Sprint Planning"),
            new FeatureDescriptor("scrum_board", "Task Board"),
            new FeatureDescriptor("estimation", "Sprint Reports"),
            new FeatureDescriptor("timeline", "Sprint TimeLine")
    );

    public ScrumTemplateImpl(ProjectRepository projectRepo,
                             SprintRepository sprintRepo,
                             StoryRepository storyRepo,
                             UserProjectRepository userProjectRepo,
                             TaskRepository taskRepo) {
        super(projectRepo, sprintRepo, storyRepo, userProjectRepo, taskRepo);
    }

    @Override
    public String getTemplateType() {
        return "scrum";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return SCRUM_FEATURES;
    }


    private static final List<FeatureDescriptor> FEATURES = List.of(
            new FeatureDescriptor("backlog", "Backlog Management"),
            new FeatureDescriptor("sprint", "Sprint Planning"),
            new FeatureDescriptor("board", "Task Board"),
            new FeatureDescriptor("report", "Sprint Reports")
    );
    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }

    @Override
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID " + projectId));

        Sprint sprint = Sprint.builder()
                .projectId(project.getId())
                .name(sprintDTO.getName())
                .startDate(sprintDTO.getStartDate())
                .endDate(sprintDTO.getEndDate())
                .status("Planned")
                .build();

        sprint = sprintRepository.save(sprint);

        sprintDTO.setId(sprint.getId());
        sprintDTO.setProjectId(project.getId());
        sprintDTO.setStatus(sprint.getStatus());

        return sprintDTO;
    }
    @Override
    public SprintDTO getSprint(Long projectId) {
        Sprint sprint = sprintRepository.findTopByProjectIdOrderByStartDateDesc(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No sprint found"));
        return new SprintDTO(sprint.getId(), projectId, sprint.getName(), sprint.getStartDate(), sprint.getEndDate(), sprint.getGoal(),sprint.getStatus());
    }

    @Override
    public List<SprintDTO> getAllSprint(Long projectId) {
        List<Sprint> sprints = sprintRepository.findByProjectId(projectId);

        if (sprints.isEmpty()) {
            return Collections.emptyList();
        }

        List<SprintDTO> sprintDTOs = new ArrayList<>();

        for (Sprint sprint : sprints) {
            SprintDTO sprintDTO = new SprintDTO(
                    sprint.getId(),
                    sprint.getProjectId(),
                    sprint.getName(),
                    sprint.getStartDate(),
                    sprint.getEndDate(),
                    sprint.getGoal(),
                    sprint.getStatus()
            );
            sprintDTOs.add(sprintDTO);
        }

        return sprintDTOs;
    }

    @Override
    public SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID " + sprintId));

        // Optional: Check if sprint belongs to the given project
        if (!sprint.getProjectId().equals(projectId)) {
            throw new ResourceNotFoundException("Sprint does not belong to project ID " + projectId);
        }

        sprint.setName(sprintDTO.getName());
        sprint.setStartDate(sprintDTO.getStartDate());
        sprint.setEndDate(sprintDTO.getEndDate());
        sprint.setGoal(sprintDTO.getGoal());
        sprint.setStatus(sprintDTO.getStatus());

        sprint = sprintRepository.save(sprint);

        return new SprintDTO(
                sprint.getId(),
                sprint.getProjectId(),
                sprint.getName(),
                sprint.getStartDate(),
                sprint.getEndDate(),
                sprint.getGoal(),
                sprint.getStatus()
        );
    }

    @Override
    public void deleteSprint(Long projectId, Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID " + sprintId));

        // Optional: Check if sprint belongs to the given project
        if (!sprint.getProjectId().equals(projectId)) {
            throw new ResourceNotFoundException("Sprint does not belong to project ID " + projectId);
        }

        sprintRepository.delete(sprint);
    }



    // You can override specific methods here if needed
}
