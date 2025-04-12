package hh.backend.goalplanner.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;
import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Status;

@RestController
public class GoalRestController {
    @Autowired
    private GoalRepository grepository;

    @Autowired
    private AppUserRepository urepository;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return urepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // Returns all goals for the current user as JSON
    @GetMapping("/api/goals")
    public List<Goal> allGoalsRest() {
        AppUser user = getCurrentUser();
        return grepository.findByUser(user);
    }

    // Returns a specific goal for the current user by its ID as JSON
    @GetMapping("/api/goals/{id}")
    public Optional<Goal> goalByIdRest(@PathVariable("id") Long goalId) {
        AppUser user = getCurrentUser();
        return grepository.findByGoalIdAndUser(goalId, user);
    }

    // Returns active goals for the current user as JSON
    // Active goals have status 'pending' and deadline in the future
    @GetMapping("api/goals/active")
    public List<Goal> activeGoalsRest() {
        AppUser user = getCurrentUser();
        LocalDate currentDate = LocalDate.now();
        return grepository.findByUserAndStatusAndDeadlineAfter(user, Status.PENDING, currentDate);
    }

    // Returns past goals for the current user as JSON
    // Past goals have status 'complete' or deadline in the past
    @GetMapping("/api/goals/past")
    public List<Goal> pastGoalsRest() {
        AppUser user = getCurrentUser();
        LocalDate currentDate = LocalDate.now();
        return grepository.findByUserAndStatusOrDeadlineBefore(user, Status.COMPLETE, currentDate);
    }
}
