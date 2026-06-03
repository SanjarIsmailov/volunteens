package sanjar.volunteens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sanjar.volunteens.repo.ProgramRepository;

@Controller
public class HomeController {

    private final ProgramRepository programRepository;

    public HomeController(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("programs", programRepository.findByActiveTrue());  // active only
        return "index";
    }
}