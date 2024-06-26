package dto.resultat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TimeDistanceResultatDTO {
    private Long deltagerId;
    private Long disciplinId;
    private LocalDate dato;
    private Float time;
    private Double distance;
}
