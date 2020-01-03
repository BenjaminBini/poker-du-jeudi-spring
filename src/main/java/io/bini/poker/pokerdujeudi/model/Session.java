package io.bini.poker.pokerdujeudi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name="session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;
    private LocalDate date;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="placeId")
    private Place place;

    private int seasonId;
}
