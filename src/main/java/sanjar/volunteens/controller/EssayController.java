package sanjar.volunteens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sanjar.volunteens.entity.Essay;
import sanjar.volunteens.entity.User;
import sanjar.volunteens.repo.EssayRepository;
import sanjar.volunteens.repo.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Controller
public class EssayController {

    private final EssayRepository essayRepository;
    private final UserRepository userRepository;

    public EssayController(EssayRepository essayRepository,
                           UserRepository userRepository) {
        this.essayRepository = essayRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {

        Optional<User> userOpt =
                userRepository.findByEmail(principal.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            model.addAttribute("user", user);
            model.addAttribute("essays",
                    essayRepository.findByAuthor(user));
        }

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

        Optional<User> userOpt =
                userRepository.findByEmail(principal.getName());

        if (userOpt.isPresent()) {

            Essay essay = new Essay();

            essay.setTitle(title);
            essay.setContent(content);
            essay.setAuthor(userOpt.get());

            essayRepository.save(essay);
        }

        return "redirect:/profile";
    }

    @GetMapping("/essay/edit/{id}")
    public String editEssay(@PathVariable Long id,
                            Principal principal,
                            Model model) {

        Optional<Essay> essayOpt =
                essayRepository.findById(id);

        if (essayOpt.isEmpty()) {
            return "redirect:/profile";
        }

        Essay essay = essayOpt.get();

        if (!essay.getAuthor()
                .getEmail()
                .equals(principal.getName())) {
            return "redirect:/profile";
        }

        model.addAttribute("essay", essay);

        return "essay-form";
    }

    @PostMapping("/essay/update/{id}")
    public String updateEssay(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String content,
                              Principal principal) {

        Optional<Essay> essayOpt =
                essayRepository.findById(id);

        if (essayOpt.isEmpty()) {
            return "redirect:/profile";
        }

        Essay essay = essayOpt.get();

        if (!essay.getAuthor()
                .getEmail()
                .equals(principal.getName())) {
            return "redirect:/profile";
        }

        essay.setTitle(title);
        essay.setContent(content);

        essayRepository.save(essay);

        return "redirect:/profile";
    }

    @PostMapping("/essay/delete/{id}")
    public String deleteEssay(@PathVariable Long id,
                              Principal principal) {

        Optional<Essay> essayOpt =
                essayRepository.findById(id);

        if (essayOpt.isEmpty()) {
            return "redirect:/profile";
        }

        Essay essay = essayOpt.get();

        if (essay.getAuthor()
                .getEmail()
                .equals(principal.getName())) {

            essayRepository.delete(essay);
        }

        return "redirect:/profile";
    }
}