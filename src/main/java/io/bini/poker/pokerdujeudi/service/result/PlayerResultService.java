package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.dto.StatDTO;
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

    public List<PlayerResult> getBySession(Session session) {
        return this.playerResultRepository.findBySession(session);
    }

    public List<PlayerResult> getByPlayer(Player player) {
        return this.playerResultRepository.findByPlayer(player);
    }

    public void delete(long playerId, long sessionId) {
        this.playerResultRepository.deleteById(new PlayerResultKey(playerId, sessionId));
    }

    public void delete(PlayerResult playerResult) {
        this.playerResultRepository.delete(playerResult);
    }

    public void save(PlayerResult playerResult) {
        this.playerResultRepository.save(playerResult);
    }

    public List<StatDTO> getStats() {
        return this.playerResultRepository.getStats();
    }
    public List<StatDTO> getStats(Integer sessionId) {
        return this.playerResultRepository.getSessionStats(sessionId);
    }
    public List<StatDTO> getSeasonStatsUntilDate(Integer sessionId) { return this.playerResultRepository.getSeasonStatsUntilDate(sessionId); }
}
