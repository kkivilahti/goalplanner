package hh.backend.goalplanner;

import java.time.LocalDate;
import java.util.List;
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
import hh.backend.goalplanner.domain.Status;

@DataJpaTest
public class GoalRepositoryTest {

    @Autowired
    GoalRepository grepository;

    @Autowired
    AppUserRepository urepository;

    private AppUser user;
    private Goal goal;

    // Run before each test
    @BeforeEach
    public void setup() {
        user = new AppUser("User", "abc123", "USER", LocalDate.of(2025, 1, 1));
        goal = new Goal("Test goal", "Description", LocalDate.of(2025, 1, 1), LocalDate.of(2040, 1, 1), Status.PENDING, user);
        
        urepository.save(user);
        grepository.save(goal);
    }

    @Test
    public void deleteById_shouldRemoveGoalFromRepository() {
        Long goalId = goal.getGoalId();

        grepository.deleteById(goalId);
        Optional<Goal> deletedGoal = grepository.findById(goalId);
        assertThat(deletedGoal).isEmpty();
    }

    @Test
    public void updatedGoalTitle_shouldBeSavedCorrectly() {
        goal.setTitle("New Title");
        grepository.save(goal);

        Optional<Goal> updated = grepository.findById(goal.getGoalId());
        assertThat(updated.get().getTitle()).isEqualTo("New Title");
    }

    @Test
    public void findByUser_shouldOnlyReturnCertainUsersGoals() {
        AppUser user2 = new AppUser("Another user", "abc123", "USER", LocalDate.of(2025, 4, 1));
        Goal goal2 = new Goal("Test goal 2", "Description", LocalDate.of(2025, 1, 1), LocalDate.of(2040, 1, 1), Status.PENDING, user2);
        urepository.save(user2);
        grepository.save(goal2);

        List<Goal> goals = grepository.findByUser(user);
        assertThat(goals).hasSize(1);

        Goal foundGoal = goals.get(0);
        assertThat(foundGoal.getUser()).isEqualTo(user);
        assertThat(foundGoal.getTitle()).isEqualTo("Test goal");
    }


    @Test
    public void findByUserAndStatusAndDeadlineAfter_shouldReturnPendingGoal() {
        LocalDate currentDate = LocalDate.now();

        List<Goal> pendingGoals = grepository.findByUserAndStatusAndDeadlineAfter(user, Status.PENDING, currentDate);
        assertThat(pendingGoals).hasSize(1);

        Goal foundGoal = pendingGoals.get(0);
        assertThat(foundGoal.getTitle()).isEqualTo("Test goal");
        assertThat(foundGoal.getStatus()).isEqualTo(Status.PENDING);

        List<Goal> completeGoals = grepository.findByUserAndStatusOrDeadlineBefore(user, Status.COMPLETE, currentDate);
        assertThat(completeGoals).isEmpty();
    }
}
