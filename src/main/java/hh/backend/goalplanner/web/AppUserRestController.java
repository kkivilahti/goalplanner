package hh.backend.goalplanner.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;

@RestController
public class AppUserRestController {
    @Autowired
    AppUserRepository urepository;

    // Returns all users as JSON
    // Only admin can see
    @GetMapping("/api/users")
    public List<AppUser> allUsersRest() {
        Iterable<AppUser> users = urepository.findAll();
        return (List<AppUser>) users;
    }

    // Returns one user as JSON
    // Only admin can see
    @GetMapping("/api/users/{id}")
    public Optional<AppUser> userByIdRest(@PathVariable("id") Long userId) {
        return urepository.findByUserId(userId);
    }
}
