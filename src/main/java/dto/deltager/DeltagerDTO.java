package dto.deltager;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeltagerDTO {

    private String navn;
    private String køn;
    private Integer alder;
    private String klub;
    private List<Long> disciplinerIds;
}
