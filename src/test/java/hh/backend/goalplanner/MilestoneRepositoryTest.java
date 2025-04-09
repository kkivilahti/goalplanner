package hh.backend.goalplanner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;
import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;

@DataJpaTest
public class MilestoneRepositoryTest {

    @Autowired
    GoalRepository grepository;

    @Autowired
    AppUserRepository urepository;

    @Autowired
    MilestoneRepository mrepository;

    private AppUser user;
    private Goal goal;
    private Milestone milestone;

    // Run before each test
    @BeforeEach
    public void setup() {
        user = new AppUser("User", "abc123", "USER", LocalDate.of(2025, 1, 1));
        goal = new Goal("Test goal", "Description", LocalDate.of(2025, 1, 1), LocalDate.of(2040, 1, 1), Status.PENDING, user);
        milestone = new Milestone("Test milestone", Status.PENDING, goal);
        urepository.save(user);
        grepository.save(goal);
        mrepository.save(milestone);
    }

    @Test
    public void findById_shouldReturnCorrectMilestone() {
        Optional<Milestone> found = mrepository.findById(milestone.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test milestone");
    }

    @Test
    public void updatedMilestoneStatus_shouldBeSavedCorrectly() {
        milestone.setStatus(Status.COMPLETE);
        mrepository.save(milestone);

        Optional<Milestone> updated = mrepository.findById(milestone.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getStatus()).isEqualTo(Status.COMPLETE);
    }

    @Test
    public void deleteById_shouldRemoveMilestoneFromRepository() {
        Long milestoneId = milestone.getId();
        mrepository.deleteById(milestoneId);

        Optional<Milestone> deleted = mrepository.findById(milestoneId);
        assertThat(deleted).isEmpty();
    }
}
