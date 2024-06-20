package dto.deltager;

import dto.disciplin.DisciplinDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeltagerWithoutResultaterDTO {

    private Long id;
    private String navn;
    private String køn;
    private int alder;
    private String klub;
    private List<DisciplinDTO> discipliner;
    private String alderGroup;
}
