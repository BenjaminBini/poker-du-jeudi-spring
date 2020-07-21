package io.bini.poker.pokerdujeudi.service.player;

import io.bini.poker.pokerdujeudi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(
            value="SELECT COUNT(*), p.firstName FROM playerResult pr " +
                    "INNER JOIN player p ON p.playerId = pr.playerId " +
                    "WHERE pr.result = (select result FROM playerResult p2 WHERE pr.sessionId = p2.sessionId ORDER BY p2.result DESC LIMIT ?1, 1) " +
                    "AND p.playerId = ?2 " +
                    "GROUP BY p.playerId " +
                    "ORDER BY COUNT(*) DESC",
            nativeQuery = true
    )
    Integer getPlayerSessionRankCount(int n, long playerId);

    @Query(value="SELECT pr.sessionId, COUNT(pr.sessionId) AS ranking FROM playerResult pr " +
            "RIGHT JOIN playerResult pr1 " +
            "ON pr1.sessionId = pr.sessionId AND (pr1.result > pr.result OR pr1.playerId = pr.playerId)" +
            "WHERE pr.playerId = ?1 " +
            "GROUP BY pr.sessionId " +
            "ORDER BY pr.sessionId DESC",
            nativeQuery = true)
    List<PlayerRank> getPlayerRankings(long playerId);

    @Query(value="SELECT COUNT(ranking) as occurence FROM ( " +
                "SELECT pr.sessionId, COUNT(pr.sessionId) AS ranking FROM playerResult pr " +
                "RIGHT JOIN playerResult pr1 ON pr1.sessionId = pr.sessionId AND (pr1.result > pr.result OR pr.playerId = pr1.playerId) " +
                "WHERE pr.playerId = ?1 " +
                "GROUP BY pr.sessionId " +
                "ORDER BY pr.sessionId DESC " +
                ") AS ranks " +
            "WHERE ranking = 1 " +
            "GROUP BY ranking ",
            nativeQuery = true)
    Integer getNumberOfFirstPlace(long playerId);

    @Query(value="SELECT COUNT(ranking) as occurence FROM ( " +
            "SELECT pr.sessionId, COUNT(pr.sessionId) AS ranking FROM playerResult pr " +
            "RIGHT JOIN playerResult pr1 ON pr1.sessionId = pr.sessionId AND (pr1.result < pr.result OR pr.playerId = pr1.playerId) " +
            "WHERE pr.playerId = ?1 " +
            "GROUP BY pr.sessionId " +
            "ORDER BY pr.sessionId DESC " +
            ") AS ranks " +
            "WHERE ranking = 1 " +
            "GROUP BY ranking ",
            nativeQuery = true)
    Integer getNumberOfLastPlace(long playerId);

    interface PlayerRank {
        long getSessionId();
        int getRanking();

        default String getRankingLabel() {
            return getRanking() + "e";
        }
    }
}
