package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.dto.StatDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class PlayerResultRestController {
    private final PlayerResultService playerResultService;

    @Autowired
    public PlayerResultRestController(PlayerResultService playerResultService) {
        this.playerResultService = playerResultService;
    }

    @RequestMapping("{sessionId}")
    public List<StatDTO> stats(@PathVariable Integer sessionId) {
        return playerResultService.getStats(sessionId);
    }

    @RequestMapping("")
    public List<StatDTO> stats() {
        return playerResultService.getStats();
    }
}
