package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity(name="session")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="seasonId")
    private Season season;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="placeId")
    private Place place;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "session")
    @JsonIgnoreProperties(value = {"session", "playerResultKey"})
    private List<PlayerResult> playerResults;

    private LocalDate date;

    public String displayDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
