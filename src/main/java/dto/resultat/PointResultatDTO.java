package dto.resultat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PointResultatDTO {
    private Long id;
    private LocalDate dato;
    private Integer points;
    private Long disciplinId;
    private Long deltagerId;
}
