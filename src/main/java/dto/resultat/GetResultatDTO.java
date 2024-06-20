package dto.resultat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GetResultatDTO {
    private Long id;
    private LocalDate dato;
    private Long deltagerId;
    private Integer points;
    private Float timeTaken;
    private Long disciplinId;
    private Double distance;
}
