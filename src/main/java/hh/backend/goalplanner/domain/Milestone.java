package hh.backend.goalplanner.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = (GenerationType.AUTO))
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "goalId")
    private Goal goal;

    public Milestone() {
    }

    public Milestone(String title, Status status, Goal goal) {
        this.title = title;
        this.status = status;
        this.goal = goal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        if (this.goal != null) {
            return "Milestone [id=" + id + ", title=" + title + ", status=" + status + ", goal=" + goal + "]";
        } else {
            return "Milestone [id=" + id + ", title=" + title + ", status=" + status + "]";
        }
    }
}
