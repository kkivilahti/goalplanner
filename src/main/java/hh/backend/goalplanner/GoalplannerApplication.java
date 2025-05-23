package hh.backend.goalplanner;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;
import hh.backend.goalplanner.domain.Goal;
import hh.backend.goalplanner.domain.GoalRepository;
import hh.backend.goalplanner.domain.Milestone;
import hh.backend.goalplanner.domain.MilestoneRepository;
import hh.backend.goalplanner.domain.Status;

@SpringBootApplication
public class GoalplannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoalplannerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(GoalRepository grepository, MilestoneRepository mrepository, AppUserRepository urepository) {
        return (args) -> {
            // create test users
            AppUser admin = new AppUser("admin", "$2a$10$SK2MNxJED0B6z1BiD7erCOlo0uHgjNyi8yJydzRbZaxQNcM.f4ilS", "ADMIN", LocalDate.of(2025, 3, 1));
            AppUser testUser = new AppUser("user", "$2a$10$6RCZo//hzwxOk1gX2KOSWu5/C9wg9zgLg0dSuApTS1wBDXQNIX5EK", "USER", LocalDate.of(2025, 3, 1));
            urepository.save(admin);
            urepository.save(testUser);

            // create demo goals with milestones
            Goal goal1 = new Goal("Try out different book genres", "Read at least one book from different genres", LocalDate.now().minusMonths(2).withDayOfMonth(1), LocalDate.now().plusMonths(6), Status.PENDING, testUser);
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

            Goal goal2 = new Goal("Self-care project", "I want to focus on myself and make better choices. Hopefully creating long-lasting habits.", LocalDate.now().minusMonths(3).withDayOfMonth(1), LocalDate.now().plusMonths(1), Status.PENDING, testUser);
            Milestone health1 = new Milestone("Start going to the gym", Status.COMPLETE, goal2);
            Milestone health2 = new Milestone("No junk food for a month", Status.PENDING, goal2);
            Milestone health3 = new Milestone("Go off social media for at least 2 weeks", Status.COMPLETE, goal2);
            Milestone health4 = new Milestone("Upgrade skin and hair care routine", Status.COMPLETE, goal2);
            Milestone health5 = new Milestone("Try out meditation", Status.COMPLETE, goal2);

            grepository.save(goal2);
            mrepository.save(health1);
            mrepository.save(health2);
            mrepository.save(health3);
            mrepository.save(health4);
            mrepository.save(health5);

            Goal goal3 = new Goal("Test goal", "This is an example goal", LocalDate.now(), LocalDate.now().plusMonths(1), Status.PENDING, admin);
            Milestone test1 = new Milestone("Example milestone", Status.COMPLETE, goal3);
            Milestone test2 = new Milestone("Another example", Status.PENDING, goal3);

            grepository.save(goal3);
            mrepository.save(test1);
            mrepository.save(test2);

        };
    }
}
