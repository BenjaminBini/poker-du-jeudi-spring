package io.bini.poker.pokerdujeudi.service.player;

import io.bini.poker.pokerdujeudi.dto.CumulatedPlayerResultDTO;
import io.bini.poker.pokerdujeudi.dto.PlayerResultDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> list() {
        return playerRepository.findAll().stream()
                .sorted(Comparator.comparingInt(p -> - p.getPlayerResults().size()))
                .collect(Collectors.toList());
    }

    public Optional<Player> get(long playerId) {
        return playerRepository.findById(playerId);
    }

    @RolesAllowed("ADMIN")
    public Player create(Player player) {
        return playerRepository.save(player);
    }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    public int getPlayerSessionRankCount(int n, long playerId) {
        Optional<PlayerRepository.PlayerRank> rankCount = this.getPlayerRankings(playerId).stream().filter(pr -> pr.getRanking() == 1).findFirst();
        return rankCount.isPresent() ? rankCount.get().getRanking() : 0;
    }

    public List<PlayerRepository.PlayerRank> getPlayerRankings(long playerId) {
        return this.playerRepository.getPlayerRankings(playerId)
                .stream()
                .sorted(Comparator.comparingInt(PlayerRepository.PlayerRank::getRanking))
                .collect(Collectors.toList());
    }
    
    public Integer getNumberOfFirstPlace(long playerId) {
        Integer numberOfFirstPlace = this.playerRepository.getNumberOfFirstPlace(playerId);
        return numberOfFirstPlace != null ? numberOfFirstPlace : 0;
    }

    public Integer getNumberOfLastPlace(long playerId) {
        Integer numberOfLastPlace = this.playerRepository.getNumberOfLastPlace(playerId);
        return numberOfLastPlace != null ? numberOfLastPlace : 0;
    }

    public List<CumulatedPlayerResultDTO> getCumulatedPlayerResults(long playerId, String season) {
        List<PlayerResult> results = this.playerRepository.getOne(playerId).getPlayerResults();
        if (season != null) {
            results = results.stream()
                    .filter(r -> r.getSession().getSeason().getName().equals(season))
                    .collect(Collectors.toList());
        }
        results = results.stream().sorted(Comparator.comparing(r -> r.getSession().getDate())).collect(Collectors.toList());
        AtomicInteger sum = new AtomicInteger(0);
        List<CumulatedPlayerResultDTO> cumulatedResults = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        results.forEach(r -> {
            CumulatedPlayerResultDTO cumulatedResult = new CumulatedPlayerResultDTO();
            cumulatedResult.setDate(r.getSession().getDate());
            cumulatedResult.setResult(r.getResult());
            cumulatedResult.setCumulatedResult(sum.accumulateAndGet(r.getResult(), Integer::sum));
            cumulatedResult.setSessionIndex(index.accumulateAndGet(0, (i1, i2) -> i1 + 1));
            cumulatedResults.add(cumulatedResult);
        });
        return cumulatedResults;
    }

    public List<PlayerResultDTO> getPlayerResults(long playerId, String season) {
        List<PlayerResult> results = this.playerRepository.getOne(playerId).getPlayerResults();
        if (season != null) {
            results = results.stream()
                    .filter(r -> r.getSession().getSeason().getName().equals(season))
                    .collect(Collectors.toList());
        }
        results = results.stream().sorted(Comparator.comparing(r -> r.getSession().getDate())).collect(Collectors.toList());
        List<PlayerResultDTO> playerResults = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        results.forEach(r -> {
            PlayerResultDTO result = new PlayerResultDTO();
            result.setSessionIndex(index.accumulateAndGet(0, (i1, i2) -> i1 + 1));
            result.setDate(r.getSession().getDate());
            result.setBuyIns(r.getBuyIns());
            result.setResult(r.getResult());
            playerResults.add(result);
        });
        return playerResults;
    }
}
