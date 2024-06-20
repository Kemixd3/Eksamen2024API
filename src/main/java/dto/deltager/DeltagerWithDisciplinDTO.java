package dto.deltager;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeltagerWithDisciplinDTO {

    @NotBlank
    private String navn;

    @NotBlank
    private String køn;

    @Min(0)
    private int alder;

    @NotBlank
    private String klub;

    @NotEmpty
    private List<Long> disciplinerIds;

    // Getters and setters
}