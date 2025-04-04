package hh.backend.goalplanner.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    // Show active (status=pending) goals on the page
    @GetMapping("/goals")
    public String showActiveGoals(Model model) {
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("activeGoals", grepository.findByStatusAndDeadlineAfter(Status.PENDING, currentDate));
        return "goals";
    }

    // Show past goals: goals that have been marked as complete, or have deadline in the past
    @GetMapping("/pastgoals")
    public String showPastGoals(Model model) {
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("pastGoals", grepository.findByStatusOrDeadlineBefore(Status.COMPLETE, currentDate));
        return "pastgoals";
    }

    // Create a form to add a new goal
    // Create a goal object with status=pending. Other information comes from user input
    @GetMapping("/addgoal")
    public String addGoal(Model model) {
        Goal goal = new Goal();
        goal.setStatus(Status.PENDING);
        model.addAttribute("goal", goal);
        return "addgoal";
    }

    // Check if goal is valid, and save the goal
    // Then redirect to /addmilestone/{id} to add milestones related to the goal
    @PostMapping("/savegoal")
    public String saveGoal(@Valid @ModelAttribute Goal goal, BindingResult result) {
        if (result.hasErrors()) {
            // In case of validation errors, return to the form and show the error messages
            return "addgoal";
        } else {
            grepository.save(goal);
            return "redirect:/addmilestone/" + goal.getGoalId();
        }
    }

    // Deleting active goal redirects to goals-page
    @GetMapping("/deletegoal/{id}")
    public String deleteActiveGoal(@PathVariable("id") Long goalId, Model model) {
        grepository.deleteById(goalId);
        return "redirect:/goals";
    }

    // Deleting past goal redirects to pastgoals-page
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

    // Save edited goal and redirect to the goal page
    @PostMapping("/savegoaledit")
    public String saveEditedGoal(@Valid @ModelAttribute Goal goal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("goal", goal);
            return "editgoal";
        }

        grepository.save(goal);

        for (Milestone milestone : goal.getMilestones()) {
            milestone.setGoal(goal);
            mrepository.save(milestone);
        }

        return "redirect:/goals";
    }

    // User can mark goals as complete
    @PostMapping("/goals/{id}/complete")
    public String markGoalAsComplete(@PathVariable("id") Long goalId) {
        Optional<Goal> optgoal = grepository.findById(goalId);
        LocalDate currentDate = LocalDate.now();

        // Check if goal object exists. Then change the status and save it
        if (optgoal.isPresent()) {
            Goal goal = optgoal.get();
            goal.setStatus(Status.COMPLETE);
            goal.setCompletionDate(currentDate);
            grepository.save(goal);
        }

        return "redirect:/pastgoals";
    }

}
