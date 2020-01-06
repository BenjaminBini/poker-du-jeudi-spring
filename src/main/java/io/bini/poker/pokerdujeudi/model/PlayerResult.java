package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="playerResult")
public class PlayerResult {
    @EmbeddedId
    private PlayerResultKey playerResultKey = new PlayerResultKey();

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "playerId")
    @JsonIgnoreProperties("playerResults")
    private Player player;

    @ManyToOne
    @MapsId("sessionId")
    @JoinColumn(name = "sessionId")
    @JsonIgnoreProperties("playerResults")
    private Session session;

    private int buyIns = 0;
    private int result = 0;
}
