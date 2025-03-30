package hh.backend.goalplanner;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;

@SpringBootApplication
public class GoalplannerApplication {

    // private static final Logger log = LoggerFactory.getLogger(GoalplannerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GoalplannerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(GoalRepository grepository, MilestoneRepository mrepository) {
        return (args) -> {
			// add demo data to the database

            Goal goal1 = new Goal("Try out different book genres", "Read at least one book from different genres during this year", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), Status.PENDING);
			Milestone books1 = new Milestone("Mystery", Status.COMPLETE, goal1);
			Milestone books2 = new Milestone("Sci-fi", Status.COMPLETE, goal1);
			Milestone books3 = new Milestone("Self-help", Status.COMPLETE, goal1);
			Milestone books4 = new Milestone("Poetry", Status.PENDING, goal1);
			Milestone books5 = new Milestone("Romance", Status.PENDING, goal1);
			Milestone books6 = new Milestone("Historical fiction", Status.PENDING, goal1);
			Milestone books7 = new Milestone("True crime", Status.PENDING, goal1);
			Milestone books8 = new Milestone("Memoir", Status.PENDING, goal1);

			grepository.save(goal1);
			mrepository.save(books1);
			mrepository.save(books2);
			mrepository.save(books3);
			mrepository.save(books4);
			mrepository.save(books5);
			mrepository.save(books6);
			mrepository.save(books7);
			mrepository.save(books8);

			Goal goal2 = new Goal("Fix eating habits", "Eat better, feel better.. Trying to eat better this spring and hopefully turn it into a lasting habit", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 5, 1), Status.PENDING);
			Milestone health1 = new Milestone("No sugar for a month", Status.COMPLETE, goal2);
			Milestone health2 = new Milestone("No fast food for a month", Status.PENDING, goal2);
			Milestone health3 = new Milestone("Add more fruits and vegetables to every meal", Status.COMPLETE, goal2);
			Milestone health4 = new Milestone("No snacking between meals", Status.COMPLETE, goal2);
			Milestone health5 = new Milestone("Cook home meals at least 4 times a week", Status.PENDING, goal2);
			
			grepository.save(goal2);
			mrepository.save(health1);
			mrepository.save(health2);
			mrepository.save(health3);
			mrepository.save(health4);
			mrepository.save(health5);

		};
    }
}
