package bf.digital.pilotage.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class AssignPermissionsRequest {

    @NotEmpty(message = "La liste des permissions ne peut pas être vide.")
    private Set<String> permissions;
}
