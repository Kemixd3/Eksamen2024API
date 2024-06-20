package dto.resultat;

import ProgrammeringsEksamenAPI.models.resultatModels.ResultatTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResultatDTO {
    private LocalDate dato;
    private Long deltagerId;
    private Long disciplinId;
    private ResultatTypeEnum resultatType;
    private double tid; // Only for TimeResultat
    private double afstand; // Only for DistanceResultat
    private int point; // Only for PointResultat
}


