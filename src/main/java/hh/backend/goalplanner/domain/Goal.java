package hh.backend.goalplanner.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="goal")
    @JsonIgnoreProperties("goal")
    private List<Milestone> milestones = new ArrayList<>();

    public Goal(String title, String description, LocalDate startDate, LocalDate deadline, Status status) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.status = status;
    }

    public Goal() {}

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    // Count days left to display on website
    public long daysLeft() {
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, this.deadline);
    }

    // Count progress% to display on website
    public int progress() {
        if (milestones == null || milestones.isEmpty()) {
            return 0;
        }

        double completed = 0;
        for (Milestone milestone : milestones) {
            if (milestone.getStatus() == Status.COMPLETE) {
                completed += 1;
            }
        }

        return (int) Math.round((completed / milestones.size()) * 100);
    }

    @Override
    public String toString() {
        return "Goal [goalId=" + goalId + ", title=" + title + ", description=" + description + ", startDate="
                + startDate + ", deadline=" + deadline + ", status=" + status + "]";
    }

}
