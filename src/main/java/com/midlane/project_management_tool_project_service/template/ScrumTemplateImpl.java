package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.Task;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.TaskRepository;
import com.midlane.project_management_tool_project_service.repository.TeamProjectRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.SprintRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.StoryRepository;

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
                             TeamProjectRepository teamProjectRepository,
                             UserProjectRepository userProjectRepository,
                             TaskRepository taskRepo) {
        super(projectRepo, sprintRepo, storyRepo, teamProjectRepository, taskRepo,userProjectRepository);
    }

    @Override
    public String getTemplateType() {
        return "scrum";
    }

    @Override
    public List<FeatureDescriptor> getAvailableFeatures() {
        return SCRUM_FEATURES;
    }


//    private static final List<FeatureDescriptor> FEATURES = List.of(
//            new FeatureDescriptor("backlog", "Backlog Management"),
//            new FeatureDescriptor("sprint", "Sprint Planning"),
//            new FeatureDescriptor("board", "Task Board"),
//            new FeatureDescriptor("report", "Sprint Reports")
//    );
    @Override
    public TaskDTO getStory(ProjectDTO dto) {
        return null;
    }

    @Override
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID " + projectId));

        // Create new sprint
        Sprint sprint = Sprint.builder()
                .projectId(project.getId())
                .name(sprintDTO.getName())
                .startDate(sprintDTO.getStartDate())
                .endDate(sprintDTO.getEndDate())
                .status("Planned")
                .build();

        sprint = sprintRepository.save(sprint);

        // Assign all unfinished tasks to this new sprint
        List<Task> unfinishedTasks = taskRepository.findByProjectId(projectId)
                .stream()
                .filter(task -> !"Done".equalsIgnoreCase(task.getStatus()))
                .toList();

        for (Task task : unfinishedTasks) {
            task.setSprintId(sprint.getId());
        }
        taskRepository.saveAll(unfinishedTasks);

        // Prepare DTO
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

        // Check if sprint belongs to the given project
        if (!sprint.getProjectId().equals(projectId)) {
            throw new ResourceNotFoundException("Sprint does not belong to project ID " + projectId);
        }

        sprint.setName(sprintDTO.getName());
        sprint.setStartDate(sprintDTO.getStartDate());
        sprint.setEndDate(sprintDTO.getEndDate());
        sprint.setGoal(sprintDTO.getGoal());
        sprint.setStatus(sprintDTO.getStatus());

        sprint = sprintRepository.save(sprint);

        // If sprint is marked Complete, unassign unfinished tasks
        if ("Complete".equalsIgnoreCase(sprintDTO.getStatus())) {
            List<Task> unfinishedTasks = taskRepository.findBySprintId(sprintId)
                    .stream()
                    .filter(task -> !"Done".equalsIgnoreCase(task.getStatus()))
                    .toList();

            for (Task task : unfinishedTasks) {
                task.setSprintId(null); // or 0 if you prefer
            }
            taskRepository.saveAll(unfinishedTasks);
        }

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

        // Validate project ownership
        if (!sprint.getProjectId().equals(projectId)) {
            throw new ResourceNotFoundException("Sprint does not belong to project ID " + projectId);
        }

        // Get all tasks in this sprint
        List<Task> tasksInSprint = taskRepository.findByProjectIdAndSprintId(projectId, sprintId);

        // Detach sprint from tasks (set to null or 0 depending on your DB schema)
        for (Task task : tasksInSprint) {
            task.setSprintId(null); // if nullable
            // OR task.setSprintId(0L); // if non-nullable (default 0 means "no sprint")
        }
        taskRepository.saveAll(tasksInSprint);

        // Now delete sprint
        sprintRepository.delete(sprint);
    }




    // You can override specific methods here if needed
}
