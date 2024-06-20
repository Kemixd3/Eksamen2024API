package ProgrammeringsEksamenAPI.models.resultatModels;

import ProgrammeringsEksamenAPI.models.Resultat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("time")
@Getter
@Setter
public class TimeResultat extends Resultat {
    private LocalDate dato;
    //private Float timeTaken;
}
