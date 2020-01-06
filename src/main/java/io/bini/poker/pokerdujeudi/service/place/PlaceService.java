package io.bini.poker.pokerdujeudi.service.place;

import io.bini.poker.pokerdujeudi.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> list() {
        return placeRepository.findAll();
    }

    public Optional<Place> get(long placeId) {
        return placeRepository.findById(placeId);
    }

    public Place create(Place place) {
        return placeRepository.save(place);
    }

    public void delete(Long id) {
        placeRepository.deleteById(id);
    }
}
