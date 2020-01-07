package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.PlayerResultKey;
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
        this.playerResultRepository.deleteById(new PlayerResultKey(playerId, sessionId));
        Session session = this.sessionRepository.getOne(sessionId);
        return session;
    }
}
