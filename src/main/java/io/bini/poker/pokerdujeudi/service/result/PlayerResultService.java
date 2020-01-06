package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerResultService {
    private final PlayerResultRepository playerResultRepository;
    private final SessionRepository sessionRepository;

    public PlayerResultService(PlayerResultRepository playerResultRepository, SessionRepository sessionRepository) {
        this.playerResultRepository = playerResultRepository;
        this.sessionRepository = sessionRepository;
    }

    public Session deletePlayerResult(long playerId, long sessionId) {
        Session session = this.sessionRepository.getOne(sessionId);
        List<PlayerResult> playerResults = session.getPlayerResults();
        playerResults.removeIf(p -> p.getPlayer().getPlayerId() == playerId);
        this.sessionRepository.save(session);
        return session;
    }
}
