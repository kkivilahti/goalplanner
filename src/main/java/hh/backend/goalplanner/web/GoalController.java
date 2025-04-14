package hh.backend.goalplanner.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;
import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;
import jakarta.validation.Valid;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository grepository;

    @Autowired
    private MilestoneRepository mrepository;

    @Autowired
    private AppUserRepository urepository;

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return urepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @GetMapping("/home")
    public String createHomePage() {
        return "home";
    }

    // Show the user's active goals (status = pending) on the page
    @GetMapping("/goals")
    public String showActiveGoals(Model model) {
        AppUser user = getCurrentUser();
        LocalDate currentDate = LocalDate.now();

        model.addAttribute("activeGoals", grepository.findByUserAndStatusAndDeadlineGreaterThanEqual(user, Status.PENDING, currentDate));
        return "goals";
    }

    // Show the user's past goals: goals that have been marked as complete, or have deadline in the past
    @GetMapping("/pastgoals")
    public String showPastGoals(Model model) {
        AppUser user = getCurrentUser();
        LocalDate currentDate = LocalDate.now();

        model.addAttribute("pastGoals", grepository.findByUserAndStatusOrDeadlineBefore(user, Status.COMPLETE, currentDate));
        return "pastgoals";
    }

    // Create a form to add a new goal
    @GetMapping("/addgoal")
    public String addGoal(Model model) {
        Goal goal = new Goal();
        goal.setStatus(Status.PENDING); // Status is 'pending' by default
        model.addAttribute("goal", goal);
        return "addgoal";
    }

    // Validate the goal, link it to the current user and save it
    // Redirect to /addmilestone/{goalid} to add milestones related to the goal
    @PostMapping("/savegoal")
    public String saveGoal(@Valid @ModelAttribute Goal goal, BindingResult result) {
        if (result.hasErrors()) {
            // In case of validation errors, return to the form and show the error messages
            return "addgoal";
        } else {
            AppUser user = getCurrentUser();
            goal.setUser(user);
            grepository.save(goal);
            return "redirect:/addmilestone/" + goal.getGoalId();
        }
    }

    // Deleting active goal redirects to /goals
    @GetMapping("/deletegoal/{id}")
    public String deleteActiveGoal(@PathVariable("id") Long goalId, Model model) {
        grepository.deleteById(goalId);
        return "redirect:/goals";
    }

    // Deleting past goal redirects to /pastgoals
    @GetMapping("/deletepastgoal/{id}")
    public String deletePastGoal(@PathVariable("id") Long goalId, Model model) {
        grepository.deleteById(goalId);
        return "redirect:/pastgoals";
    }

    // Create a form to edit goal
    // Find goal info by id
    @GetMapping("/editgoal/{id}")
    public String editGoal(@PathVariable("id") Long goalId, Model model) {
        Optional<Goal> goal = grepository.findById(goalId);
        model.addAttribute("goal", goal.get());
        return "editgoal";
    }

    // Save tedited goal and redirect to the 'Active goals' page
    @PostMapping("/savegoaledit")
    public String saveEditedGoal(@Valid @ModelAttribute Goal goal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // In case of validation errors, return to the form and show the error messages
            model.addAttribute("goal", goal);
            return "editgoal";
        }

        // Make sure that goal is linked to user
        AppUser user = getCurrentUser();
        goal.setUser(user);
        grepository.save(goal);

        for (Milestone milestone : goal.getMilestones()) {
            milestone.setGoal(goal);
            mrepository.save(milestone);
        }

        return "redirect:/goals";
    }

    // Allows the user to mark goals as complete
    @PostMapping("/goals/{id}/complete")
    public String markGoalAsComplete(@PathVariable("id") Long goalId) {
        Optional<Goal> optgoal = grepository.findById(goalId);
        LocalDate currentDate = LocalDate.now();

        // If the goal exists, update the status and completion date and save it
        if (optgoal.isPresent()) {
            Goal goal = optgoal.get();
            goal.setStatus(Status.COMPLETE);
            goal.setCompletionDate(currentDate);
            grepository.save(goal);
        }

        return "redirect:/pastgoals";
    }

}
