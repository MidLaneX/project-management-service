package com.midlane.project_management_tool_project_service.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class KafkaHealthService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @EventListener(ApplicationReadyEvent.class)
    public void checkKafkaConnection() {
        log.info("🔄 Project Service - Checking Kafka connection to: {}", bootstrapServers);

        CompletableFuture.runAsync(() -> {
            try {
                Properties props = new Properties();
                props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
                props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
                props.put(AdminClientConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 10000);

                try (AdminClient adminClient = AdminClient.create(props)) {
                    // Try to list topics to verify connection
                    adminClient.listTopics().names().get(5, TimeUnit.SECONDS);
                    log.info("✅ Project Service - Successfully connected to Kafka at: {}", bootstrapServers);
                    log.info("📥 Project Service - Kafka consumer is ready to receive team member events");
                } catch (Exception e) {
                    log.error("❌ Project Service - Failed to connect to Kafka at: {}. Error: {}", bootstrapServers, e.getMessage());
                    log.warn("⚠️  Project Service - Team member events will not be processed until Kafka is available");
                }
            } catch (Exception e) {
                log.error("❌ Project Service - Error during Kafka health check: {}", e.getMessage());
            }
        });
    }
}
