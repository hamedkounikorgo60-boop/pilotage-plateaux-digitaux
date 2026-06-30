package bf.digital.pilotage.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class AssignerTacheRequest {

    @NotEmpty(message = "La liste des membres à assigner ne peut pas être vide.")
    private Set<Long> assignesIds;
}
