package io.bini.poker.pokerdujeudi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name="season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seasonId;
    private Integer name;
}
