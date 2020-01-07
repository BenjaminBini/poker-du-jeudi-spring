package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name="player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playerId;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "player")
    @JsonIgnoreProperties(value = {"player", "playerResultKey"})
    private List<PlayerResult> playerResults;

    private String firstName;
    private String lastName;
    private Long placeId;

}
