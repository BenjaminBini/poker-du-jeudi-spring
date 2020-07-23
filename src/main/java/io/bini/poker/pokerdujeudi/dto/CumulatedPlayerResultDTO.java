package io.bini.poker.pokerdujeudi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CumulatedPlayerResultDTO {
    private Date date;
    private String dateLabel;
    private int result;
    private int cumulatedResult;
    private long sessionIndex;
}
