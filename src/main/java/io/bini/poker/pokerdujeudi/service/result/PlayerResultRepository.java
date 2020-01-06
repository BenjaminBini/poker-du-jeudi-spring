package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.PlayerResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerResultRepository extends JpaRepository<PlayerResult, PlayerResultKey> {
}
