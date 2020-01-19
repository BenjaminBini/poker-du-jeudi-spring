package io.bini.poker.pokerdujeudi.service.result;

import io.bini.poker.pokerdujeudi.dto.StatDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.PlayerResultKey;
import io.bini.poker.pokerdujeudi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerResultRepository extends JpaRepository<PlayerResult, PlayerResultKey> {
    List<PlayerResult> findBySession(Session session);
    List<PlayerResult> findByPlayer(Player player);


    @Query(value = "SELECT p.firstName, s.date, pr.result, pr.buyIns, ss.name as year " +
            "FROM playerResult pr " +
            "INNER JOIN player p " +
            "ON p.playerId = pr.playerId " +
            "INNER JOIN session s " +
            "ON s.sessionId = pr.sessionId " +
            "INNER JOIN season ss " +
            "ON ss.seasonId = s.seasonId;", nativeQuery=true)
    public List<StatDTO> getStats();


    @Query(value = "SELECT p.firstName, s.date, pr.result, pr.buyIns, ss.name as year " +
            "FROM playerResult pr " +
            "INNER JOIN player p " +
            "ON p.playerId = pr.playerId " +
            "INNER JOIN session s " +
            "ON s.sessionId = pr.sessionId " +
            "INNER JOIN season ss " +
            "ON ss.seasonId = s.seasonId where s.sessionId = ?1", nativeQuery=true)
    public List<StatDTO> getSessionStats(Integer sessionId);


    @Query(value = "SELECT p.firstName, s.date, pr.result, pr.buyIns, ss.name as year " +
            "FROM playerResult pr " +
            "INNER JOIN player p ON p.playerId = pr.playerId " +
            "INNER JOIN session s ON s.sessionId = pr.sessionId  " +
            "INNER JOIN season ss ON ss.seasonId = s.seasonId " +
            "WHERE s.seasonId IN (SELECT seasonId FROM session WHERE sessionId = ?1)" +
            "AND s.date <= (SELECT date FROM session WHERE sessionId = ?1);", nativeQuery=true)
    public List<StatDTO> getSeasonStatsUntilDate(Integer sessionId);
}
