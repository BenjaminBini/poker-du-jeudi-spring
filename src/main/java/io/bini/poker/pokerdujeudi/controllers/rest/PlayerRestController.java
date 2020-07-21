package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.dto.CumulatedPlayerResultDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerRestController {
    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping("")
    public List<Player> getPlayers() {
        return playerService.list();
    }
}
