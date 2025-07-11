package hh.backend.goalplanner.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;
import jakarta.validation.Valid;

@Controller
public class MilestoneController {

    @Autowired
    private MilestoneRepository mrepository;

    @Autowired
    private GoalRepository grepository;

    // Create a form page to add new milestones
    // Link new milestones to the right goal with goalId
    @GetMapping("/addmilestone/{goalId}")
    public String addMilestone(@PathVariable Long goalId, Model model) {
        Optional<Goal> goal = grepository.findById(goalId);

        if (goal.isPresent()) {
            Milestone milestone = new Milestone();
            model.addAttribute("goal", goal.get());
            model.addAttribute("milestone", milestone);

            boolean milestoneEmpty = goal.get().getMilestones().isEmpty();
            model.addAttribute("milestoneEmpty", milestoneEmpty);
        } else {
            // If goal doesn't exist
            return "redirect:/goals";
        }

        return "addmilestones";
    }

    // Save milestone and link it to the right goal
    @PostMapping("/savemilestone")
    public String saveMilestone(@ModelAttribute @Valid Milestone milestone, BindingResult result, @RequestParam Long goalId, Model model) {
        Optional<Goal> optgoal = grepository.findById(goalId);
        Goal goal = optgoal.get();

        if (result.hasErrors()) {
            // In case of validation errors, return to the form and show the error messages
            model.addAttribute("goal", goal);
            model.addAttribute("milestoneEmpty", goal.getMilestones().isEmpty());
            return "addmilestones";
        }

        milestone.setGoal(goal);
        milestone.setStatus(Status.PENDING); // Status is 'pending' by default
        mrepository.save(milestone);

        // Redirect back to /addmilestone, in case user wants to add more milestones to the goal
        return "redirect:/addmilestone/" + goalId;
    }

    // Delete a milestone on the 'Active goals' page
    @GetMapping("/deletemilestone/{id}")
    public String deleteMilestone(@PathVariable("id") Long id) {
        mrepository.deleteById(id);
        return "redirect:/goals";
    }

    // Delete a milestone on the 'Add milestones' page
    @GetMapping("/deletemilestone/{id}/fromgoal/{goalId}")
    public String deleteMilestoneInAddForm(@PathVariable("id") Long id, @PathVariable("goalId") Long goalId) {
        mrepository.deleteById(id);
        return "redirect:/addmilestone/" + goalId;
    }

    // Allows the user to mark milestones as complete
    // Helps with visual tracking: milestone indicators update their color based on milestone status
    @PostMapping("/milestones/{id}/complete")
    public String markAsComplete(@PathVariable("id") Long id) {
        Optional<Milestone> optmilestone = mrepository.findById(id);

        // If the milestone exists, update the status and save it
        if (optmilestone.isPresent()) {
            Milestone milestone = optmilestone.get();
            milestone.setStatus(Status.COMPLETE);
            mrepository.save(milestone);
        }

        return "redirect:/goals";
    }

}
