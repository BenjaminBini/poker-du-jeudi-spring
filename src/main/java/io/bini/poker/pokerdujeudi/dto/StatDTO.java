package io.bini.poker.pokerdujeudi.dto;

import lombok.Data;

import java.util.Date;

public interface StatDTO {
    String getFirstName();
    Integer getResult();
    Integer getBuyIns();
    Date getDate();
    Integer getYear();
}
