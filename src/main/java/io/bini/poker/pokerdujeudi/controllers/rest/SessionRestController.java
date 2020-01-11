package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.dto.SessionResultDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {
    private final SessionService sessionService;
    private final PlayerResultService playerResultService;
    private final PlayerService playerService;

    public SessionRestController(SessionService sessionService, PlayerResultService playerResultService, PlayerService playerService) {
        this.sessionService = sessionService;
        this.playerResultService = playerResultService;
        this.playerService = playerService;
    }

    @GetMapping("")
    public List<Session> getSessions() {
        return this.sessionService.list();
    }

    @GetMapping("{sessionId}")
    public Session getSession(@PathVariable long sessionId) {
        return this.sessionService.get(sessionId).get();
    }

    @PostMapping("{sessionId}/player/{playerId}")
    public Session updatePlayerResults(@PathVariable long sessionId, @PathVariable long playerId, @RequestBody SessionResultDTO sessionResultDTO) {
        Session session = this.sessionService.get(sessionId).get();
        Optional<PlayerResult> maybePlayerResult = session.getPlayerResults().stream().filter(s -> s.getPlayer().getPlayerId() == playerId).findFirst();
        if (!maybePlayerResult.isPresent()) {
            Optional<Player> player = this.playerService.get(playerId);
            PlayerResult playerResult = new PlayerResult();
            playerResult.setPlayer(player.get());
            playerResult.setSession(session);
            this.playerResultService.save(playerResult);
            session.getPlayerResults().add(playerResult);
        }
        if (maybePlayerResult.isPresent()) {
            PlayerResult playerResult = maybePlayerResult.get();
            playerResult.setBuyIns(sessionResultDTO.getBuyIns());
            playerResult.setResult(sessionResultDTO.getResult());
        }
        this.sessionService.save(session);
        return session;
    }

    @DeleteMapping("{sessionId}/player/{playerId}")
    public Session deletePlayerResult(@PathVariable long sessionId, @PathVariable long playerId) {
        this.playerResultService.delete(playerId, sessionId);
        Session session = this.sessionService.get(sessionId).get();
        session.getPlayerResults().removeIf(r -> r.getPlayer().getPlayerId() == playerId);
        return session;
    }

}
