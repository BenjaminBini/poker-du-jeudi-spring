package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.PlayerResultKey;
import io.bini.poker.pokerdujeudi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerResultRepository extends JpaRepository<PlayerResult, PlayerResultKey> {
    List<PlayerResult> findBySession(Session session);
    List<PlayerResult> findByPlayer(Player player);
}
