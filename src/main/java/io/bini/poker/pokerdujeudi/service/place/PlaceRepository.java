package io.bini.poker.pokerdujeudi.service.place;

import io.bini.poker.pokerdujeudi.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
