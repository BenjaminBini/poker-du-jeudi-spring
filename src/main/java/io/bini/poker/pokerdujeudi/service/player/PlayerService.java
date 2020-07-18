package io.bini.poker.pokerdujeudi.service.player;

import io.bini.poker.pokerdujeudi.model.Player;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        return this.playerRepository.getPlayerSessionRankCount(n, playerId);
    }

    public List<PlayerRepository.PlayerRank> getPlayerRankings(long playerId) {
        return this.playerRepository.getPlayerRankings(playerId);
    }
}
