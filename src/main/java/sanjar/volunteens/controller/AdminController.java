package sanjar.volunteens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sanjar.volunteens.entity.Program;
import sanjar.volunteens.repo.EssayRepository;
import sanjar.volunteens.repo.ProgramRepository;
import sanjar.volunteens.repo.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final EssayRepository essayRepository;
    private final ProgramRepository programRepository;

    public AdminController(UserRepository userRepository,
                           EssayRepository essayRepository,
                           ProgramRepository programRepository) {
        this.userRepository = userRepository;
        this.essayRepository = essayRepository;
        this.programRepository = programRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("essays", essayRepository.findAll());
        model.addAttribute("programs", programRepository.findAllByOrderByIdDesc());
        return "admin";
    }

    @PostMapping("/users/toggle-admin/{id}")
    public String toggleAdmin(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(u -> {
            u.setAdmin(!u.isAdmin());
            userRepository.save(u);
        });
        return "redirect:/admin";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String email,
                             @RequestParam String phoneNumber,
                             RedirectAttributes ra) {
        userRepository.findById(id).ifPresent(u -> {
            userRepository.findByEmail(email)
                    .filter(other -> !other.getId().equals(id))
                    .ifPresentOrElse(
                            other -> ra.addFlashAttribute("error", "Email already in use."),
                            () -> {
                                u.setName(name);
                                u.setSurname(surname);
                                u.setEmail(email);
                                u.setPhoneNumber(phoneNumber);
                                userRepository.save(u);
                            }
                    );
        });
        return "redirect:/admin";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/essays/delete/{id}")
    public String deleteEssay(@PathVariable Long id) {
        essayRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/programs/save")
    public String saveProgram(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam String link,
                              @RequestParam(defaultValue = "false") boolean active) {
        Program p = new Program();
        p.setTitle(title);
        p.setDescription(description);
        p.setLink(link);
        p.setActive(active);
        programRepository.save(p);
        return "redirect:/admin";
    }

    @PostMapping("/programs/update/{id}")
    public String updateProgram(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String description,
                                @RequestParam String link,
                                @RequestParam(defaultValue = "false") boolean active) {
        programRepository.findById(id).ifPresent(p -> {
            p.setTitle(title);
            p.setDescription(description);
            p.setLink(link);
            p.setActive(active);
            programRepository.save(p);
        });
        return "redirect:/admin";
    }

    @PostMapping("/programs/delete/{id}")
    public String deleteProgram(@PathVariable Long id) {
        programRepository.deleteById(id);
        return "redirect:/admin";
    }
}