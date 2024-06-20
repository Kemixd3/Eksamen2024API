package dto.disciplin;

import dto.resultat.GetResultatDTO;
import dto.resultat.ResultatDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetDisciplinDTO {
    private Long id;
    private String navn;
    private String resultattype;
    private List<GetResultatDTO> resultater;
}
