package bf.digital.pilotage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePlateauRequest {

    @NotBlank(message = "Le nom du plateau est obligatoire.")
    private String nom;

    private String description;
}
