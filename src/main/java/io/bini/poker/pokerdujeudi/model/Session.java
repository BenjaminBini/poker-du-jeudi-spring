package io.bini.poker.pokerdujeudi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
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

    private Date date;

    public String displayDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy");
        return format.format(this.date);
    }

    @JsonIgnore
    public PlayerResult getBestResult() {
        return this.playerResults.stream().max(Comparator.comparingInt(PlayerResult::getResult)).get();
    }

    @JsonIgnore
    public PlayerResult getWorstResult() {
        return this.playerResults.stream().min(Comparator.comparingInt(PlayerResult::getResult)).get();
    }

    @JsonIgnore
    public int getChangeValue() {
        return - this.playerResults.stream().mapToInt(PlayerResult::getResult).sum();
    }

}
