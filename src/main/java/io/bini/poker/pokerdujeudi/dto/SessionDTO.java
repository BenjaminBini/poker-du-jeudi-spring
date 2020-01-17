package io.bini.poker.pokerdujeudi.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class SessionDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    private List<Long> playerIds;
    private long placeId;
    private long seasonId;
}
