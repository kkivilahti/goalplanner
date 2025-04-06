package hh.backend.goalplanner.domain;

import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUserId(Long userId);
    AppUser findByUsername(String username);
}
