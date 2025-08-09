package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.handler.GlobalExceptionHandler;
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

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScrumTemplateImpl implements ScrumTemplate {

    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final StoryRepository storyRepository;
    private final UserProjectRepository userProjectRepository;
    private final GlobalExceptionHandler globalExceptionHandler;


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

    // use for assign people for projects
    public UserProjectDTO createUserProject(ProjectDTO projectDTO, UserProjectRequestDTO userProjectRequestDTO) {
        if (projectDTO.getId() == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }

        UserProject userProject = UserProject.builder()
                .projectId(projectDTO.getId())
                .userId(userProjectRequestDTO.getUserId())
                .role(userProjectRequestDTO.getRole())
                .build();

        UserProjectDTO userProjectDTO=new UserProjectDTO();
        userProjectDTO.setProjectId(projectDTO.getId());
        userProjectDTO.setUserId(userProjectRequestDTO.getUserId());
        userProjectDTO.setRole(userProjectRequestDTO.getRole());

        userProjectRepository.save(userProject);

        return userProjectDTO;


    }

    @Override
    public List<UserProjectDTO> getUsersOfProject(ProjectDTO projectDTO) {
        if (projectDTO.getId() == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        List<UserProject> userProjects=userProjectRepository.findByProjectId(projectDTO.getId());
        List<UserProjectDTO> usersOfProject=new ArrayList<>();
        for (UserProject userProject : userProjects) {
            UserProjectDTO dto =new UserProjectDTO();

            dto.setRole(userProject.getRole());
            dto.setProjectId(projectDTO.getId());
            dto.setUserId(userProject.getUserId());
            usersOfProject.add(dto);
        }
        return usersOfProject;
    }


    @Override
    public List<ProjectDTO> getProjectsOfUser(UserProjectRequestDTO userProjectRequestDTO) {
        if (userProjectRequestDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        List<UserProject> userProjects = userProjectRepository.findByUserId(userProjectRequestDTO.getUserId());
        List<ProjectDTO> projectsOfUser = new ArrayList<>();

        for (UserProject userProject : userProjects) {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(userProject.getId());
            projectsOfUser.add(dto);

        }

        return projectsOfUser;
    }


}
