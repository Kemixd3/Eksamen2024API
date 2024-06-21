package ProgrammeringsEksamenAPI.models.resultatModels;

import ProgrammeringsEksamenAPI.models.Resultat;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("point")
@Getter
@Setter
public class PointResultat extends Resultat {


}
