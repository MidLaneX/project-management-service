package com.midlane.project_management_tool_project_service.repository;

import com.midlane.project_management_tool_project_service.model.UserProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserProjectRepositoryTest {

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Test
    void findByUserIdAndOrgId_ShouldReturnUserProjects() {
        UserProject up = UserProject.builder()
                .userId(1L)
                .orgId(1L)
                .role("ADMIN")
                .build();
        userProjectRepository.save(up);

        List<UserProject> result = userProjectRepository.findByUserIdAndOrgId(1L, 1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRole()).isEqualTo("ADMIN");
    }
}
