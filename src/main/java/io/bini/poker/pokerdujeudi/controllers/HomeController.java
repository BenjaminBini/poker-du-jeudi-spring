package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    private final SessionService sessionService;

    @Autowired
    public HomeController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("active", "home");
        Session lastSession = this.sessionService.getLastSession();
        //lastSession = this.sessionService.get(56).get();
        model.addAttribute("lastSession", lastSession);
        return "home";
    }
}
