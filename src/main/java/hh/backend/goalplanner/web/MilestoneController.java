package hh.backend.goalplanner.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;

@Controller
public class MilestoneController {

    @Autowired
    private MilestoneRepository mrepository;

    @Autowired
    private GoalRepository grepository;

    // Link new milestones to the right goal with goalId
    @GetMapping("/addmilestone/{goalId}")
    public String addMilestone(@PathVariable Long goalId, Model model) {
        Optional<Goal> goal = grepository.findById(goalId);
        Milestone milestone = new Milestone();
        model.addAttribute("goal", goal.get());
        model.addAttribute("milestone", milestone);
        return "addmilestones";
    }

    // Save new milestone with status=pending and link it to the right goal
    // Redirect back to /addmilestones, in case user wants to add more milestones to the goal
    @PostMapping("/savemilestone")
    public String saveMilestone(@RequestParam Long goalId, @RequestParam String title) {
        Optional<Goal> goal = grepository.findById(goalId);
        Milestone milestone = new Milestone();
        milestone.setTitle(title);
        milestone.setStatus(Status.PENDING);
        milestone.setGoal(goal.get());
        mrepository.save(milestone);
        return "redirect:/addmilestone/" + goalId;
    }

    // User can mark milestones as complete
    // Helps with visual tracking: milestone indicators update their color based on milestone status
    @PostMapping("/milestones/{id}/complete")
    public String markAsComplete(@PathVariable Long id) {
        Optional<Milestone> optmilestone = mrepository.findById(id);

        // Check if milestone object exists. Then change the status and save it
        if (optmilestone.isPresent()) {
            Milestone milestone = optmilestone.get();
            milestone.setStatus(Status.COMPLETE);
            mrepository.save(milestone);
        }

        return "redirect:/goals";
    }
}
