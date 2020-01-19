package io.bini.poker.pokerdujeudi.service.session;

import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final PlayerResultService playerResultService;

    public SessionService(SessionRepository sessionRepository, PlayerResultService playerResultService) {
        this.sessionRepository = sessionRepository;
        this.playerResultService = playerResultService;
    }

    public List<Session> list() {
        List<Session> sessions = sessionRepository.findAll();
        sessions.sort(Comparator.comparing(Session::getDate));
        return sessions;
    }
    public Optional<Session> get(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public void delete(Long id) {
        Session session = sessionRepository.getOne(id);
        session.getPlayerResults().stream().forEach(r -> playerResultService.delete(r));
        sessionRepository.deleteById(id);
    }

    public Session getLastSession() {
        return this.sessionRepository.getLastSession();
    }

    public Session getPreviousSession(long sessionId) {
        return this.sessionRepository.getSessionBefore(sessionId);
    }

    public Session getNextSession(long sessionId) {
        return this.sessionRepository.getSessionAfter(sessionId);
    }
}
