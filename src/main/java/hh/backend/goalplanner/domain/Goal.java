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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Future(message = "Deadline must be in the future")
    @NotNull(message = "Deadline is required")
    private LocalDate deadline;

    private LocalDate completionDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="goal")
    @JsonIgnoreProperties("goal")
    @Valid
    private List<Milestone> milestones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("goals")
    private AppUser user;

    public Goal(String title, String description, LocalDate startDate, LocalDate deadline, Status status, AppUser user) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.status = status;
        this.user = user;
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

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    // Count days left to display on website
    public long daysLeft() {
        LocalDate currentDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(currentDate, this.deadline);

        return days;
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

        int progress = (int) Math.round((completed / milestones.size()) * 100);

        return progress;
    }

    @Override
    public String toString() {
        return "Goal [goalId=" + goalId + ", title=" + title + ", description=" + description + ", startDate="
                + startDate + ", deadline=" + deadline + ", status=" + status + "]";
    }

}
