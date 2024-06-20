package ProgrammeringsEksamenAPI.models.resultatModels;

import ProgrammeringsEksamenAPI.models.Resultat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("distance")
@Getter
@Setter
public class DistanceResultat extends Resultat {
    private Double distance;  // in meters
}
