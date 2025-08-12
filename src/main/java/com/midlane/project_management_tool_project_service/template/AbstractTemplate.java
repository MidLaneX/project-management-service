package com.midlane.project_management_tool_project_service.template;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.*;
import com.midlane.project_management_tool_project_service.model.featureItemModel.*;
import com.midlane.project_management_tool_project_service.repository.*;
import com.midlane.project_management_tool_project_service.repository.featureRepository.*;




import java.util.ArrayList;
import java.util.List;


public abstract class AbstractTemplate implements Template {

    protected final ProjectRepository projectRepository;
    protected final SprintRepository sprintRepository;
    protected final StoryRepository storyRepository;
    protected final UserProjectRepository userProjectRepository;
    protected  final  TaskRepository taskRepository;




    protected AbstractTemplate(ProjectRepository projectRepo,
                               SprintRepository sprintRepo,
                               StoryRepository storyRepo,
                               UserProjectRepository userProjectRepo,
                               TaskRepository taskRepo) {
        this.projectRepository = projectRepo;
        this.sprintRepository = sprintRepo;
        this.storyRepository = storyRepo;
        this.userProjectRepository = userProjectRepo;
        this.taskRepository = taskRepo;

    }

    @Override
    public abstract String getTemplateType();

    @Override
    public abstract List<FeatureDescriptor> getAvailableFeatures();

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        Project project = Project.builder()
                .name(dto.getName())
                .templateType(getTemplateType())
                .features(getFeatureKeys()) // extract keys only
                .build();
        project = projectRepository.save(project);
        dto.setId(project.getId());
        dto.setFeatures(project.getFeatures());
        return dto;
    }

    @Override
    public ProjectDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // No DB fetch for features — return from current template's available features
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getTemplateType(),
                getFeatureKeys()
        );
    }






//    @Override
//    public TaskDTO getStory(ProjectDTO dto) {
//        List<Story> stories = storyRepository.findByProjectId(dto.getId());
//        if (stories.isEmpty()) throw new ResourceNotFoundException("No stories found");
//
//        Story s = stories.get(0);
//        return new TaskDTO(s.getId(), s.getProjectId(), s.getSprintId(), s.getTitle(), s.getDescription(), s.getStatus(), s.getStoryPoints());
//    }

    @Override
    public UserProjectDTO createUserProject(ProjectDTO dto, UserProjectRequestDTO userDTO) {
        UserProject userProject = UserProject.builder()
                .projectId(dto.getId())
                .userId(userDTO.getUserId())
                .role(userDTO.getRole())
                .build();
        userProjectRepository.save(userProject);
        return new UserProjectDTO(dto.getId(), userDTO.getUserId(), userDTO.getRole());
    }

    @Override
    public List<UserProjectDTO> getUsersOfProject(ProjectDTO dto) {
        List<UserProject> entities = userProjectRepository.findByProjectId(dto.getId());
        List<UserProjectDTO> list = new ArrayList<>();
        for (UserProject entity : entities) {
            list.add(new UserProjectDTO(entity.getProjectId(), entity.getUserId(), entity.getRole()));
        }
        return list;
    }

    @Override
    public List<ProjectDTO> getProjectsOfUser(Long userId) {
        List<UserProject> userProjects = userProjectRepository.findByUserId(userId);
        List<ProjectDTO> result = new ArrayList<>();
        for (UserProject up : userProjects) {
            Project project = projectRepository.findById(up.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            result.add(new ProjectDTO(project.getId(), project.getName(), project.getTemplateType(), project.getFeatures()));
        }
        return result;
    }


    @Override
    public TaskDTO createStory(Long projectId, TaskDTO taskDTO) {
        Sprint sprint = null;
        if (taskDTO.getSprintId() != null) {
            sprint = sprintRepository.findById(taskDTO.getSprintId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID " + taskDTO.getSprintId()));
        }

        Story story = Story.builder()
                .projectId(projectId)
                .sprintId(sprint != null ? sprint.getId() : null)
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus() != null ? taskDTO.getStatus() : "To Do")
                .storyPoints(taskDTO.getStoryPoints())
                .build();

        story = storyRepository.save(story);

        taskDTO.setId(story.getId());
        taskDTO.setProjectId(story.getProjectId());
        return taskDTO;
    }


    private List<String> getFeatureKeys() {
        return getAvailableFeatures().stream().map(FeatureDescriptor::getKey).toList();
    }




    // Tasks  Mainly needed for functionalities
    public TaskDTO createTask(Long projectId, TaskDTO dto) {
        Task task = Task.builder()
                .projectId(projectId)
                .sprintId(dto.getSprintId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .assignee(dto.getAssignee())
                .reporter(dto.getReporter())
                .dueDate(dto.getDueDate())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .type(dto.getType())
                .storyPoints(dto.getStoryPoints())
                .labels(dto.getLabels())
                .build();

        task = taskRepository.save(task);
        dto.setId(task.getId());
        return dto;
    }

    // Task - Get all by project
    public List<TaskDTO> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream().map(this::toDto).toList();
    }

    // Task - Get by ID
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    // Task - Update
    public TaskDTO updateTask(Long id, TaskDTO updates) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setTitle(updates.getTitle());
        task.setDescription(updates.getDescription());
        task.setAssignee(updates.getAssignee());
        task.setReporter(updates.getReporter());
        task.setDueDate(updates.getDueDate());
        task.setPriority(updates.getPriority());
        task.setStatus(updates.getStatus());
        task.setType(updates.getType());
        task.setStoryPoints(updates.getStoryPoints());
        task.setLabels(updates.getLabels());
        task = taskRepository.save(task);
        return toDto(task);
    }

    public  TaskDTO updateSprint(Long id,Long sprintId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setSprintId(sprintId);
        task = taskRepository.save(task);
        return toDto(task);

    }

    // Task - Delete
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Task - Update Status
    public TaskDTO updateTaskStatus(Long id, String newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(newStatus);
        task = taskRepository.save(task);
        return toDto(task);
    }

    // Utility - Convert entity to DTO
    private TaskDTO toDto(Task t) {
        return new TaskDTO(
                t.getId(),
                t.getProjectId(),
                t.getSprintId(),
                t.getTitle(),
                t.getDescription(),
                t.getAssignee(),
                t.getReporter(),
                t.getDueDate(),
                t.getPriority(),
                t.getStatus(),
                t.getType(),
                t.getStoryPoints(),
                t.getLabels(),
                List.of() // optional: handle comments if needed
        );
    }



    public abstract SprintDTO getSprint(Long projectId);

    public abstract List<SprintDTO> getAllSprint(Long projectId);

    public abstract SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO);

    public abstract void deleteSprint(Long projectId, Long sprintId);
}
