package io.bini.poker.pokerdujeudi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResultKey implements Serializable {
    private long playerId;
    private long sessionId;
}
