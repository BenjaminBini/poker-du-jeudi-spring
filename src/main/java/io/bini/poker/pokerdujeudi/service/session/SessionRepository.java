package io.bini.poker.pokerdujeudi.service.session;

import io.bini.poker.pokerdujeudi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "SELECT * FROM session ORDER BY date desc LIMIT 0, 1;", nativeQuery=true)
    public Session getLastSession();
}
