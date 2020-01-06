package io.bini.poker.pokerdujeudi.service.season;

import io.bini.poker.pokerdujeudi.model.Season;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public List<Season> list() {
        return seasonRepository.findAll();
    }
    
    public Optional<Season> get(long seasonId) {
        return seasonRepository.findById(seasonId);
    }

    public Season create(Season season) {
        return seasonRepository.save(season);
    }

    public void delete(Long id) {
        seasonRepository.deleteById(id);
    }
}
