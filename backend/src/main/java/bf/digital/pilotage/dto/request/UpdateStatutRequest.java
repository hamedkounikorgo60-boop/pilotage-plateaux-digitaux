package bf.digital.pilotage.dto.request;

import bf.digital.pilotage.enums.StatutTache;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatutRequest {

    @NotNull(message = "Le statut est obligatoire.")
    private StatutTache statut;

    private String commentaireBlocage;
}
