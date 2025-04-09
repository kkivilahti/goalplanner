package hh.backend.goalplanner;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.backend.goalplanner.web.AppUserController;
import hh.backend.goalplanner.web.GoalController;
import hh.backend.goalplanner.web.MilestoneController;

// Smoke testing
@SpringBootTest
class GoalplannerApplicationTests {

	@Autowired
	GoalController gcontroller;

	@Autowired
	MilestoneController mcontroller;

	@Autowired
	AppUserController ucontroller;

	@Test
	void contextLoads() throws Exception {
		assertThat(gcontroller).isNotNull();
		assertThat(mcontroller).isNotNull();
		assertThat(ucontroller).isNotNull();
	}

}
