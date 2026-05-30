package sanjar.volunteens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EssayController {

    @GetMapping("/essay/new")
    public String newEssay() {
        return "essay-form";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}