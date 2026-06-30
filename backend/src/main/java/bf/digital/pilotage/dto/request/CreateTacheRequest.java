package bf.digital.pilotage.dto.request;

import bf.digital.pilotage.enums.PrioriteTache;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CreateTacheRequest {

    @NotBlank(message = "Le titre est obligatoire.")
    private String titre;

    private String description;

    private PrioriteTache priorite;

    private LocalDate echeance;

    @NotNull(message = "Le plateau est obligatoire.")
    private Long plateauId;

    private Set<Long> assignesIds;
}
