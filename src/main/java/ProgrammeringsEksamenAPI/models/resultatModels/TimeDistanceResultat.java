package ProgrammeringsEksamenAPI.models.resultatModels;

import ProgrammeringsEksamenAPI.models.Resultat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("time_distance")
@Getter
@Setter
public class TimeDistanceResultat extends Resultat {
    private Float timeTaken;     // in seconds
    private Double distance; // in meters
    //private LocalDate dato;
}