package hh.backend.goalplanner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;

@DataJpaTest
public class AppUserRepositoryTest {
    @Autowired
    AppUserRepository urepository;

    private AppUser user;

    // Run before each test
    @BeforeEach
    public void setup() {
        user = new AppUser("User", "abc123", "USER", LocalDate.of(2025, 1, 1));
        urepository.save(user);
    }

    @Test
    public void findByUsername_shouldReturnCorrectUser() {
        Optional<AppUser> found = urepository.findByUsername(user.getUsername());
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("User");
    }

    @Test
    public void deleteById_shouldRemoveUserFromRepository() {
        Long userId = user.getUserId();
        urepository.deleteById(userId);

        Optional<AppUser> deleted = urepository.findByUserId(userId);
        assertThat(deleted).isEmpty();
    }
}
