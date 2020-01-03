package io.bini.poker.pokerdujeudi.service.season;

import io.bini.poker.pokerdujeudi.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
}
