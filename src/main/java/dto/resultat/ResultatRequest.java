package dto.resultat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResultatRequest {
    private Long deltagerId;
    private LocalDate dato;
    private Integer points;
    private Float timeTaken;
    private Double distance;
    private String resultatType;
}
