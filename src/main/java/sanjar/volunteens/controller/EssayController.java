package sanjar.volunteens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sanjar.volunteens.entity.Essay;
import sanjar.volunteens.repo.EssayRepository;
import sanjar.volunteens.repo.UserRepository;

import java.security.Principal;

@Controller
public class EssayController {

    private final EssayRepository essayRepository;
    private final UserRepository  userRepository;

    public EssayController(EssayRepository essayRepository,
                           UserRepository  userRepository) {
        this.essayRepository = essayRepository;
        this.userRepository  = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        userRepository.findByEmail(principal.getName()).ifPresent(user -> {
            model.addAttribute("user", user);
            model.addAttribute("essays", essayRepository.findByAuthor(user));
        });
        return "profile";
    }

    @GetMapping("/essay/new")
    public String newEssay() {
        return "essay-form";
    }

    @PostMapping("/essay/save")
    public String saveEssay(@RequestParam String title,
                            @RequestParam String content,
                            Principal principal) {
        userRepository.findByEmail(principal.getName()).ifPresent(user -> {
            Essay essay = new Essay();
            essay.setTitle(title);
            essay.setContent(content);
            essay.setAuthor(user);
            essayRepository.save(essay);
        });
        return "redirect:/profile";
    }
}