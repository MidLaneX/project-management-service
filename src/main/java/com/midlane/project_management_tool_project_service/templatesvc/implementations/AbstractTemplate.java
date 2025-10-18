package com.midlane.project_management_tool_project_service.templatesvc.implementations;

import com.midlane.project_management_tool_project_service.dto.*;
import com.midlane.project_management_tool_project_service.exception.ResourceNotFoundException;
import com.midlane.project_management_tool_project_service.model.*;
import com.midlane.project_management_tool_project_service.repository.*;
import com.midlane.project_management_tool_project_service.repository.featureRepository.*;
import com.midlane.project_management_tool_project_service.templatesvc.SprintCapableTemplate;
import com.midlane.project_management_tool_project_service.templatesvc.Template;
import jakarta.transaction.Transactional;


import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class AbstractTemplate implements Template {

    protected final ProjectRepository projectRepository;
    protected final SprintRepository sprintRepository;
    protected  final  TaskRepository taskRepository;
    protected  final UserProjectRepository userProjectRepository;





    protected AbstractTemplate(ProjectRepository projectRepo,
                               SprintRepository sprintRepo,
                               TaskRepository taskRepo,
                               UserProjectRepository userProjectRepository) {
        this.projectRepository = projectRepo;
        this.sprintRepository = sprintRepo;
        this.taskRepository = taskRepo;
        this.userProjectRepository = userProjectRepository;
    }

    @Override
    public abstract String getTemplateType();

    @Override
    public abstract List<SprintCapableTemplate.FeatureDescriptor> getAvailableFeatures();

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        Long userId = dto.getUserId();
        Long orgId = dto.getOrgId();

        if (userId == null || orgId == null) {
            throw new IllegalArgumentException("Both userId (createdBy) and orgId are required to create a project.");
        }

        boolean isAdmin = !userProjectRepository
                .findByUserIdAndOrgIdAndRole(userId, orgId, "ADMIN")
                .isEmpty();

        if (!isAdmin) {
            throw new SecurityException("Access denied: Only ADMIN users can create projects in this organization.");
        }

        Project project = Project.builder()
                .name(dto.getName())
                .type(dto.getType())
                .templateType(getTemplateType())
                .features(getFeatureKeys())
                .orgId(orgId)
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .build();

        project = projectRepository.save(project);
        dto.setId(project.getId());
        dto.setFeatures(project.getFeatures());
        return dto;
    }



    @Override
    public List<ProjectDTO> getProjectsForUser(Long userId, Long orgId) {
        List<ProjectDTO> result = new ArrayList<>();

        //  Check if user is ADMIN in this org
        List<UserProject> adminAssignments = userProjectRepository.findByUserIdAndOrgIdAndRole(userId, orgId, "ADMIN");
        if (!adminAssignments.isEmpty()) {
            // Admin → fetch all projects in org
            List<Project> projects = projectRepository.findByOrgId(orgId);
            for (Project p : projects) {
                result.add(new ProjectDTO(p.getId(), p.getName(),p.getType(), p.getTemplateType(), p.getFeatures()));
            }
            return result;
        }

        //  Non-admin → fetch projects assigned to user's teams
        List<UserProject> assignments = userProjectRepository.findByUserIdAndOrgId(userId, orgId);
        List<Long> projectIds = assignments.stream()
                .map(UserProject::getProjectId)
                .distinct()
                .toList();

        if (!projectIds.isEmpty()) {
            List<Project> projects = projectRepository.findAllById(projectIds);
            for (Project p : projects) {
                result.add(new ProjectDTO(p.getId(), p.getName(),p.getType(), p.getTemplateType(), p.getFeatures()));
            }
        }

        return result;
    }



    @Override
    public ProjectDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // No DB fetch for features — return from current template's available features
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getType(),
                project.getTemplateType(),
                getFeatureKeys()
        );
    }

    @Override
    public ProjectDTO updateProject(Long userId, Long projectId, ProjectDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // Check if user is ADMIN for this org
        boolean isAdmin = !userProjectRepository
                .findByUserIdAndOrgIdAndRole(userId, project.getOrgId(), "ADMIN")
                .isEmpty();

        if (!isAdmin) {
            throw new SecurityException("Access denied: Only ADMIN can update this project");
        }

        // Update allowed fields
        if (dto.getName() != null) project.setName(dto.getName());
        if (dto.getFeatures() != null) project.setFeatures(dto.getFeatures());

        project = projectRepository.save(project);

        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getType(),
                project.getTemplateType(),
                project.getFeatures()
        );
    }

    @Override
    public void deleteProject(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        //  Check if user is ADMIN for this org
        boolean isAdmin = !userProjectRepository
                .findByUserIdAndOrgIdAndRole(userId, project.getOrgId(), "ADMIN")
                .isEmpty();

        if (!isAdmin) {
            throw new SecurityException("Access denied: Only ADMIN can delete this project");
        }

        projectRepository.deleteById(projectId);
    }

    @Transactional
    @Override
    public List<UserProjectDTO> assignTeamToProject(Long userId, Long projectId, Long teamId) throws AccessDeniedException {
        // Get orgId from any member of the team
        UserProject sample = userProjectRepository.findByTeamId(teamId).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No users found for teamId " + teamId));
        Long orgId = sample.getOrgId();

        // Check if calling user is ADMIN in this org
        boolean isAdmin = !userProjectRepository
                .findByUserIdAndOrgIdAndRole(userId, orgId, "ADMIN")
                .isEmpty();

        if (!isAdmin) {
            throw new AccessDeniedException("Only ADMIN users can assign or reassign teams to projects.");
        }

        //  Fetch all users in the team
        List<UserProject> teamUsers = userProjectRepository.findByTeamId(teamId);
        if (teamUsers.isEmpty()) {
            throw new ResourceNotFoundException("No users found for teamId " + teamId);
        }

        //  Clear old projects assigned to this team
        List<Long> oldProjectIds = userProjectRepository.findProjectIdsByTeamId(teamId);
        if (!oldProjectIds.isEmpty()) {
            for (UserProject up : teamUsers) {
                up.setProjectId(null);
            }
            userProjectRepository.saveAll(teamUsers);
        }

        // Clear any existing team previously assigned to this project
        List<UserProject> otherTeams = userProjectRepository.findByProjectId(projectId);
        for (UserProject up : otherTeams) {
            if (!Objects.equals(up.getTeamId(), teamId)) {
                up.setProjectId(null);
            }
        }
        userProjectRepository.saveAll(otherTeams);

        //  Assign the new project to this team
        for (UserProject userProject : teamUsers) {
            userProject.setProjectId(projectId);
        }
        List<UserProject> updatedUsers = userProjectRepository.saveAll(teamUsers);

        // Convert entities to DTOs
        List<UserProjectDTO> result = new ArrayList<>();
        for (UserProject userProject : updatedUsers) {
            UserProjectDTO dto = new UserProjectDTO();
            dto.setId(userProject.getId());
            dto.setProjectId(userProject.getProjectId());
            dto.setUserId(userProject.getUserId());
            dto.setRole(userProject.getRole());
            result.add(dto);
        }

        return result;
    }



//    public List<UserProjectDTO> assignTeamToProject(Long projectId, Long teamId) {
//        // 1. Fetch all users in the team
//        List<UserProject> teamUsers = userProjectRepository.findByTeamId(teamId);
//        if (teamUsers.isEmpty()) {
//            throw new ResourceNotFoundException("No users found for teamId " + teamId);
//        }
//
//        // 2. Update each user with the new projectId
//        for (UserProject userProject : teamUsers) {
//            userProject.setProjectId(projectId);
//        }
//
//        // 3. Save all updated records
//        List<UserProject> updatedUsers = userProjectRepository.saveAll(teamUsers);
//
//        // 4. Convert entities to DTOs manually
//        List<UserProjectDTO> result = new ArrayList<>();
//        for (UserProject userProject : updatedUsers) {
//            UserProjectDTO dto = new UserProjectDTO();
//            dto.setId(userProject.getId());
//            dto.setProjectId(userProject.getProjectId());
//            dto.setUserId(userProject.getUserId());
//            dto.setRole(userProject.getRole());
//            result.add(dto);
//        }
//
//        return result;
//    }

    @Override
    public Long getAssignedTeamOfProject(Long projectId) {

        boolean projectExists = userProjectRepository.existsByProjectId(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Project with ID " + projectId + " not found");
        }


        return userProjectRepository.findFirstTeamIdByProjectId(projectId);
    }









//    @Override
//    public TaskDTO getStory(ProjectDTO dto) {
//        List<Story> stories = storyRepository.findByProjectId(dto.getId());
//        if (stories.isEmpty()) throw new ResourceNotFoundException("No stories found");
//
//        Story s = stories.get(0);
//        return new TaskDTO(s.getId(), s.getProjectId(), s.getSprintId(), s.getTitle(), s.getDescription(), s.getStatus(), s.getStoryPoints());
//    }
//======================Not Importent======================================
//    @Override
//    public UserProjectDTO createUserProject(ProjectDTO dto, UserProjectRequestDTO userDTO) {
//        UserProject userProject = UserProject.builder()
//                .projectId(dto.getId())
//                .userId(userDTO.getUserId())
//                .role(userDTO.getRole())
//                .build();
//        userProjectRepository.save(userProject);
//        return new UserProjectDTO(dto.getId(), userDTO.getUserId(), userDTO.getRole());
//    }

//    @Override
//    public List<UserProjectDTO> getUsersOfProject(ProjectDTO dto) {
//        List<UserProject> entities = TeamProjectRepository.findByProjectId(dto.getId());
//        List<UserProjectDTO> list = new ArrayList<>();
//        for (UserProject entity : entities) {
//            list.add(new UserProjectDTO(entity.getProjectId(), entity.getUserId(), entity.getRole()));
//        }
//        return list;
//    }

//    @Override
//    public List<ProjectDTO> getProjectsOfUser(Long userId) {
//        List<UserProject> userProjects = userProjectRepository.findByUserId(userId);
//        List<ProjectDTO> result = new ArrayList<>();
//        for (UserProject up : userProjects) {
//            Project project = projectRepository.findById(up.getProjectId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//            result.add(new ProjectDTO(project.getId(), project.getName(), project.getTemplateType(), project.getFeatures()));
//        }
//        return result;
//    }


//    @Override
//    public TaskDTO createStory(Long projectId, TaskDTO taskDTO) {
//        Sprint sprint = null;
//        if (taskDTO.getSprintId() != null) {
//            sprint = sprintRepository.findById(taskDTO.getSprintId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID " + taskDTO.getSprintId()));
//        }
//
//        Story story = Story.builder()
//                .projectId(projectId)
//                .sprintId(sprint != null ? sprint.getId() : null)
//                .title(taskDTO.getTitle())
//                .description(taskDTO.getDescription())
//                .status(taskDTO.getStatus() != null ? taskDTO.getStatus() : "To Do")
//                .storyPoints(taskDTO.getStoryPoints())
//                .build();
//
//        story = storyRepository.save(story);
//
//        taskDTO.setId(story.getId());
//        taskDTO.setProjectId(story.getProjectId());
//        return taskDTO;
//    }


    private List<String> getFeatureKeys() {
        return getAvailableFeatures().stream().map(SprintCapableTemplate.FeatureDescriptor::getKey).toList();
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
                .epic(dto.getEpic())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .type(dto.getType())
                .storyPoints(dto.getStoryPoints())
                .labels(dto.getLabels())
                .build();

        task = taskRepository.save(task);
        dto.setId(task.getId());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
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
        task.setEpic(updates.getEpic());
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
                t.getEpic(),
                t.getPriority(),
                t.getStatus(),
                t.getType(),
                t.getStoryPoints(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getLabels(),
                List.of() // optional: handle comments if needed
        );
    }


//
//    public abstract SprintDTO getSprint(Long projectId);
//
//    public abstract List<SprintDTO> getAllSprint(Long projectId);
//
//    public abstract SprintDTO updateSprint(Long projectId, Long sprintId, SprintDTO sprintDTO);
//
//    public abstract void deleteSprint(Long projectId, Long sprintId);
}
