package hh.backend.goalplanner.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.backend.goalplanner.domain.AppUser;
import hh.backend.goalplanner.domain.AppUserRepository;
import hh.backend.goalplanner.domain.SignUpForm;
import jakarta.validation.Valid;

@Controller
public class AppUserController {
    @Autowired
    private AppUserRepository repository;

    // Create login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Create sign up form
    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }

    // Create new user
    @PostMapping("/saveuser")
    public String saveUser(@Valid @ModelAttribute("signupform") SignUpForm signUpForm, BindingResult result) {
        if (result.hasErrors()) {
            // Check validation errors
            return "signup";
        } else {
            // Check password match
            if (signUpForm.getPassword().equals(signUpForm.getPasswordCheck())) {
                String password = signUpForm.getPassword();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String passwordHash = passwordEncoder.encode(password);

                AppUser newUser = new AppUser();
                newUser.setPasswordHash(passwordHash);
                newUser.setUsername(signUpForm.getUsername());
                newUser.setRole("USER");
                newUser.setCreatedAt(LocalDate.now());

                // Check if username exists
                if (!repository.findByUsername(signUpForm.getUsername()).isPresent()) {
                    repository.save(newUser);
                } else {
                    result.rejectValue("username", "err.username", "Username already exists");
                    return "signup";
                }
            } else {
                result.rejectValue("passwordCheck", "err.passCheck", "Passwords don't match");
                return "signup";
            }
        }

        return "redirect:login";
    }

    // Create page for admin to view users
    @GetMapping("/userlist")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users";
    }

    // Admin can delete users
    @GetMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") Long userId, AppUser user) {
        repository.deleteById(userId);
        return "redirect:/userlist";
    }

}
