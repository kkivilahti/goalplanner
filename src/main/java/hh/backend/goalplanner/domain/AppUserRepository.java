package hh.backend.goalplanner.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByUserId(Long userId);
    Optional<AppUser> findByUsername(String username);
}
