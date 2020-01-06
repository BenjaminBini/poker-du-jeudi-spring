package io.bini.poker.pokerdujeudi.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PlayerResultKey implements Serializable {
    private long playerId;
    private long sessionId;
}
