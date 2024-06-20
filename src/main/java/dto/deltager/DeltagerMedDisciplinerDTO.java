package dto.deltager;

import dto.disciplin.DisciplinDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeltagerMedDisciplinerDTO {

    private Long id;
    private String navn;
    private String køn;
    private Integer alder;
    private String klub;
    private List<DisciplinDTO> disciplinerIds;
    private String alderGroup;
}