package io.bini.poker.pokerdujeudi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name="place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeId;
    private String name;
}
