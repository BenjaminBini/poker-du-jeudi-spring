package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.dto.CumulatedPlayerResultDTO;
import io.bini.poker.pokerdujeudi.dto.PlayerResultDTO;
import io.bini.poker.pokerdujeudi.dto.StatDTO;
import io.bini.poker.pokerdujeudi.service.player.PlayerRepository;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class PlayerResultRestController {
    private final PlayerResultService playerResultService;
    private final PlayerService playerService;

    @Autowired
    public PlayerResultRestController(PlayerResultService playerResultService, PlayerService playerService) {
        this.playerResultService = playerResultService;
        this.playerService = playerService;
    }

    @RequestMapping("{sessionId}")
    public List<StatDTO> stats(@PathVariable Integer sessionId) {
        return playerResultService.getStats(sessionId);
    }

    @RequestMapping("")
    public List<StatDTO> stats() {
        return playerResultService.getStats();
    }

    @RequestMapping("until/{sessionId}")
    public List<StatDTO> statsUntilSession(@PathVariable Integer sessionId) {
        return playerResultService.getSeasonStatsUntilDate(sessionId);
    }

    @RequestMapping("player/{playerId}/rankings")
    public List<PlayerRepository.PlayerRank> getRanks(@PathVariable Long playerId) {
        return playerService.getPlayerRankings(playerId);
    }

    @RequestMapping("player/{playerId}/sum")
    public List<CumulatedPlayerResultDTO> getCumulatedResults(@PathVariable long playerId,
                                                              @RequestParam(required = false) String season) {
        return playerService.getCumulatedPlayerResults(playerId, season);
    }

    @RequestMapping("player/{playerId}/results")
    public List<PlayerResultDTO> getResults(@PathVariable long playerId,
                                            @RequestParam(required = false) String season) {
        return playerService.getPlayerResults(playerId, season);
    }
}
