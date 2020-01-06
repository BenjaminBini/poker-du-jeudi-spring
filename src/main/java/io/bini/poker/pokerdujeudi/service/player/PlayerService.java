package io.bini.poker.pokerdujeudi.service.player;

import io.bini.poker.pokerdujeudi.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> list() {
        return playerRepository.findAll();
    }

    public Optional<Player> get(long playerId) {
        return playerRepository.findById(playerId);
    }

    public Player create(Player player) {
        return playerRepository.save(player);
    }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
