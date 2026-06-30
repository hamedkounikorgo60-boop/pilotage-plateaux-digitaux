package bf.digital.pilotage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignRoleRequest {

    @NotBlank(message = "Le rôle est obligatoire.")
    private String role;
}
