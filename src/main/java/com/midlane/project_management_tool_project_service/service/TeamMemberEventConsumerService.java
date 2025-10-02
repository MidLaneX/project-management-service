package com.midlane.project_management_tool_project_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midlane.project_management_tool_project_service.dto.TeamMemberAddedEventDto;
import com.midlane.project_management_tool_project_service.model.UserProject;
import com.midlane.project_management_tool_project_service.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberEventConsumerService {

    private final UserProjectRepository userProjectRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "team-member-added", groupId = "project-service-group")
    @Transactional
    public void handleTeamMemberAddedEvent(ConsumerRecord<String, Object> record) {
        try {
            log.info("📨 Received team member added event from topic: {}, partition: {}, offset: {}",
                    record.topic(), record.partition(), record.offset());

            Object messageValue = record.value();
            log.info("📨 Event message: {}", messageValue);

            // Convert the deserialized object to DTO
            TeamMemberAddedEventDto event;
            if (messageValue instanceof String) {
                // If somehow it's still a string, parse it
                event = objectMapper.readValue((String) messageValue, TeamMemberAddedEventDto.class);
            } else {
                // If it's already deserialized (LinkedHashMap), convert it
                event = objectMapper.convertValue(messageValue, TeamMemberAddedEventDto.class);
            }

            log.info("🔄 Processing team member added event - UserId: {}, TeamId: {}, OrgId: {}, Role: {}",
                    event.getUserId(), event.getTeamId(), event.getOrganizationId(), event.getRole());

            // Check if user is already in the team for this organization
            boolean exists = userProjectRepository.findByUserIdAndOrgId(event.getUserId(), event.getOrganizationId())
                    .stream()
                    .anyMatch(up -> up.getTeamId().equals(event.getTeamId()));

            if (exists) {
                log.warn("⚠️ User {} is already a member of team {} in organization {}",
                        event.getUserId(), event.getTeamId(), event.getOrganizationId());
                return;
            }

            // Create new UserProject entry
            UserProject userProject = UserProject.builder()
                    .userId(event.getUserId())
                    .orgId(event.getOrganizationId())
                    .teamId(event.getTeamId())
                    .role(event.getRole())
                    .projectId(null) // Set to null as specified in requirements
                    .build();

            UserProject savedUserProject = userProjectRepository.save(userProject);

            log.info("✅ Successfully created UserProject entry with ID: {} for User: {} in Team: {} (Org: {})",
                    savedUserProject.getId(), event.getUserId(), event.getTeamId(), event.getOrganizationId());

        } catch (JsonProcessingException e) {
            log.error("❌ Failed to parse team member added event JSON: {}", record.value(), e);
        } catch (Exception e) {
            log.error("❌ Error processing team member added event: {}", record.value(), e);
            throw e; // Re-throw to trigger retry mechanism
        }
    }
}
