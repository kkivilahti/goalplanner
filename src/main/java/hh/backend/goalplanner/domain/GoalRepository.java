package hh.backend.goalplanner.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface GoalRepository extends CrudRepository<Goal, Long> {
    List<Goal> findByStatusAndDeadlineAfter(Status status, LocalDate currentDate);
    List<Goal> findByStatusOrDeadlineBefore(Status status, LocalDate currentDate);
}
