package io.bini.poker.pokerdujeudi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerResultDTO {
    private int buyIns;
    private int result;
    private Date date;
    private int sessionIndex;
}
