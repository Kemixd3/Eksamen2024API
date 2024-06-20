package dto.resultat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TimeDistanceResultatDTO {
    private Long deltagerId;
    private Long disciplinId;
    private LocalDate dato;
    private Double time;
    private Double distance;
}
