package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("")
    public String players(Model model) {
        List<Player> players = this.playerService.list();
        model.addAttribute("players", players);
        return "players";
    }

    @PostMapping("")
    public String addPlayer(@ModelAttribute Player player) {
        this.playerService.create(player);
        return "redirect:/players";
    }

    @GetMapping("delete/{playerId}")
    public String deletePlayer(@PathVariable Long playerId) {
        this.playerService.delete(playerId);
        return "redirect:/players";
    }

}
