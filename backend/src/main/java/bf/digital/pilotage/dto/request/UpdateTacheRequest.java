package bf.digital.pilotage.dto.request;

import bf.digital.pilotage.enums.PrioriteTache;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTacheRequest {

    @NotBlank(message = "Le titre est obligatoire.")
    private String titre;

    private String description;

    private PrioriteTache priorite;

    private LocalDate echeance;
}
