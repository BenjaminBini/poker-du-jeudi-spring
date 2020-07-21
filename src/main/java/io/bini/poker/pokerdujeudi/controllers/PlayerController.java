package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerResultService playerResultService;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerResultService playerResultService) {
        this.playerService = playerService;
        this.playerResultService = playerResultService;
    }

    @GetMapping("")
    public String players(Model model) {
        List<Player> players = this.playerService.list();
        model.addAttribute("players", players);
        model.addAttribute("active", "players");
        return "players";
    }


    @GetMapping("{playerId}")
    public String player(Model model, @PathVariable long playerId) {
        Optional<Player> player = this.playerService.get(playerId);
        model.addAttribute("player", player.get());
        model.addAttribute("active", "players");
        model.addAttribute("numberOfFirstPlace", this.playerService.getNumberOfFirstPlace(playerId));
        model.addAttribute("numberOfLastPlace", this.playerService.getNumberOfLastPlace(playerId));
        model.addAttribute("rankings", this.playerService.getPlayerRankings(playerId));
        return "player";
    }

    @PostMapping("")
    public String addPlayer(@ModelAttribute Player player) {
        this.playerService.create(player);
        return "redirect:/players";
    }

    @GetMapping("delete/{playerId}")
    public String deletePlayer(@PathVariable Long playerId) {
        Player player = this.playerService.get(playerId).get();
        player.getPlayerResults().forEach(r -> this.playerResultService.delete(r));
        this.playerService.delete(playerId);
        return "redirect:/players";
    }

}
