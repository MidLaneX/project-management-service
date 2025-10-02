package com.midlane.project_management_tool_project_service.repository.featureRepository;

import com.midlane.project_management_tool_project_service.model.featureItemModel.Sprint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SprintRepositoryTest {

    @Autowired
    private SprintRepository sprintRepository;

    @Test
    void findByProjectId_ShouldReturnSprints() {
        Sprint sprint = Sprint.builder()
                .projectId(1L)
                .name("Sprint 1")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(14))
                .status("Active")
                .build();

        sprintRepository.save(sprint);

        List<Sprint> result = sprintRepository.findByProjectId(1L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Sprint 1");
    }

    @Test
    void findTopByProjectIdOrderByStartDateDesc_ShouldReturnLatestSprint() {
        Sprint sprint1 = Sprint.builder()
                .projectId(1L)
                .startDate(LocalDate.now().minusDays(5))
                .build();

        Sprint sprint2 = Sprint.builder()
                .projectId(1L)
                .startDate(LocalDate.now())
                .build();

        sprintRepository.save(sprint1);
        sprintRepository.save(sprint2);

        Optional<Sprint> latest = sprintRepository.findTopByProjectIdOrderByStartDateDesc(1L);
        assertThat(latest).isPresent();
        assertThat(latest.get().getStartDate()).isEqualTo(sprint2.getStartDate());
    }
}
