package hh.backend.goalplanner.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Status;


@Controller
public class GoalController {

    @Autowired
    private GoalRepository grepository;

    // Show all goals on the page
    @GetMapping("/goals")
    public String showGoals(Model model) {
        model.addAttribute("goals", grepository.findAll());
        return "goals";
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

    // First, save goal
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
    
}
