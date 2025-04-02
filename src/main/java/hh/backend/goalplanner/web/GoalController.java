package hh.backend.goalplanner.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository grepository;

    @Autowired
    private MilestoneRepository mrepository;

    // Show active goals on the page
    @GetMapping("/goals")
    public String showActiveGoals(Model model) {
        model.addAttribute("goals", grepository.findByStatus(Status.PENDING));
        return "goals";
    }

    // Show past goals: goals that have status=complete or deadline in the past
    @GetMapping("/pastgoals")
    public String showPastGoals(Model model) {
        model.addAttribute("completeGoals", grepository.findByStatus(Status.COMPLETE));

        LocalDate currentDate = LocalDate.now();
        model.addAttribute("pastGoals", grepository.findByDeadlineBefore(currentDate));
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

    // First, save new goal
    // Then redirect to /addmilestone/{id} to add milestones related to the goal
    @PostMapping("/savegoal")
    public String saveGoal(@ModelAttribute Goal goal) {
        grepository.save(goal);
        return "redirect:/addmilestone/" + goal.getGoalId();
    }

    @GetMapping("/deletegoal/{id}")
    public String deleteGoal(@PathVariable("id") Long goalId, Model model) {
        grepository.deleteById(goalId);
        return "redirect:/goals";
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
    public String saveEditedGoal(@ModelAttribute Goal goal) {
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

        return "redirect:/goals";
    }

}
