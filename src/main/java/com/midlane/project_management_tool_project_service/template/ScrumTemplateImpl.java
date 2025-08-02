package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.ProjectDTO;
import com.midlane.project_management_tool_project_service.dto.UserProjectDTO;
import com.midlane.project_management_tool_project_service.dto.SprintDTO;
import com.midlane.project_management_tool_project_service.dto.StoryDTO;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.Project;
import com.midlane.project_management_tool_project_service.model.UserProject;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import com.midlane.project_management_tool_project_service.model.featureItemModel.Story;
import com.midlane.project_management_tool_project_service.repository.ProjectRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.SprintRepository;
import com.midlane.project_management_tool_project_service.repository.featureRepository.StoryRepository;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScrumTemplateImpl implements ScrumTemplate {

    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final StoryRepository storyRepository;
    private final UserProjectRepository userProjectRepository;




    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        Project project = Project.builder()
                .name(dto.getName())
                .templateType("scrum")
                .features(List.of("sprint", "backlog", "scrum_board", "estimation"))
                .build();

        project = projectRepository.save(project);

        dto.setId(project.getId());
        dto.setFeatures(project.getFeatures());
        return dto;
    }
    @Override
    public ProjectDTO getProject(ProjectDTO dto){
        Project project = projectRepository.findById(dto.getId()).get();
        dto.setName(project.getName());
        dto.setTemplateType(project.getTemplateType());
        dto.setFeatures(project.getFeatures());
        return dto;
    }

    @Override
    public SprintDTO createSprint(Long projectId, SprintDTO sprintDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        Sprint sprint = Sprint.builder()
                .projectId(project.getId())
                .name(sprintDTO.getName())
                .startDate(sprintDTO.getStartDate())
                .endDate(sprintDTO.getEndDate())
                .status("Planned")
                .build();

        sprint = sprintRepository.save(sprint);

        sprintDTO.setId(sprint.getId());
        sprintDTO.setStatus(sprint.getStatus());
        sprintDTO.setProjectId(project.getId());
        return sprintDTO;
    }

    @Override
    public SprintDTO getSprint(ProjectDTO dto){
        Sprint sprint = sprintRepository.findTopByProjectIdOrderByStartDateDesc(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No sprint found"));
         SprintDTO sprintDTO=new SprintDTO();
         sprintDTO.setId(sprint.getId());
         sprintDTO.setName(sprint.getName());
         sprintDTO.setStartDate(sprint.getStartDate());
         sprintDTO.setEndDate(sprint.getEndDate());
         sprintDTO.setStatus(sprint.getStatus());
         sprintDTO.setProjectId(dto.getId());
         return sprintDTO;

     }

    @Override
    public StoryDTO createStory(Long projectId, StoryDTO storyDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        Sprint sprint = null;
        if (storyDTO.getSprintId() != null) {
            sprint = sprintRepository.findById(storyDTO.getSprintId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id " + storyDTO.getSprintId()));
        }

        Story story = Story.builder()
                .projectId(project.getId())
                .sprintId(sprint.getId())
                .title(storyDTO.getTitle())
                .description(storyDTO.getDescription())
                .status(storyDTO.getStatus() != null ? storyDTO.getStatus() : "To Do")
                .storyPoints(storyDTO.getStoryPoints())
                .build();

        story = storyRepository.save(story);

        storyDTO.setId(story.getId());
        return storyDTO;
    }
    public StoryDTO getStory(ProjectDTO dto){
        List<Story> stories=storyRepository.findByProjectId(dto.getId());
        StoryDTO storyDTO=new StoryDTO();
        storyDTO.setProjectId(dto.getId());
        storyDTO.setStoryPoints(stories.get(0).getStoryPoints());
        storyDTO.setTitle(stories.get(0).getTitle());
        storyDTO.setDescription(stories.get(0).getDescription());
        storyDTO.setStatus(stories.get(0).getStatus());
        return  storyDTO;
    }


    public UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectDTO userProjectDTO) {
        if (projectDTO.getId() != null) {
            userProjectDTO.setProjectId(projectDTO.getId());
        }

        // Map DTO to Entity
        UserProject userProject = UserProject.builder()
                .projectId(userProjectDTO.getProjectId())
                .userId(userProjectDTO.getUserId())
                .role(userProjectDTO.getRole())
                .build();

        // Save entity


        userProject = userProjectRepository.save(userProject);
         projectDTO.setId(userProject.getId());
         return userProjectDTO;


    }

    public UserProjectDTO getUserProject(ProjectDTO dto) {
        List<UserProject> userProjects=userProjectRepository.findByProjectId(dto.getId());
        UserProjectDTO userProjectDTO1=new UserProjectDTO();
        userProjectDTO1.setProjectId(dto.getId());
        userProjectDTO1.setUserId(userProjects.get(0).getUserId());
        userProjectDTO1.setRole(userProjects.get(0).getRole());
        return userProjectDTO1;
    }

}
