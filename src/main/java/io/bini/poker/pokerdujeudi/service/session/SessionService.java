package io.bini.poker.pokerdujeudi.service.session;

import io.bini.poker.pokerdujeudi.model.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> list() {
        return sessionRepository.findAll();
    }

    public Optional<Session> get(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

}
