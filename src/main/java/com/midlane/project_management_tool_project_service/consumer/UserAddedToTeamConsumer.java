//package com.midlane.project_management_tool_project_service.consumer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.midlane.project_management_tool_project_service.dto.UserAddedToTeamEvent;
//import com.midlane.project_management_tool_project_service.model.UserProject;
//import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserAddedToTeamConsumer {
//
//    private final UserProjectRepository userProjectRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @KafkaListener(topics = "user-added-to-team", groupId = "project-service-group")
//    public void consume(String message) {
//        try {
//            log.info("Received Kafka message: {}", message);
//
//            UserAddedToTeamEvent event = objectMapper.readValue(message, UserAddedToTeamEvent.class);
//
//            // TODO: find projectId by teamId (your business logic here)
//            Long projectId = findProjectIdByTeamId(event.getTeamId());
//
//            UserProject userProject = UserProject.builder()
//                    .orgId(event.getOrgId())
//                    .teamId(event.getTeamId())
//                    .projectId(projectId)
//                    .userId(event.getUserId())
//                    .role(event.getRole())
//                    .build();
//
//            userProjectRepository.save(userProject);
//
//            log.info("UserProject saved for user {} in project {}", event.getUserId(), projectId);
//
//        } catch (Exception e) {
//            log.error("Failed to process Kafka message", e);
//        }
//    }
//
//    private Long findProjectIdByTeamId(Long teamId) {
//        // implement lookup (maybe from TeamProjectRepository or service)
//        return 1L; // dummy for now
//    }
//}
