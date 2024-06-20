package ProgrammeringsEksamenAPI.models.resultatModels;

import ProgrammeringsEksamenAPI.models.Resultat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("time_distance")
@Getter
@Setter
public class TimeDistanceResultat extends Resultat {
    private Double time;     // in seconds
    private Double distance; // in meters
}