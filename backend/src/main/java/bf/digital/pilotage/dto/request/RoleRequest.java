package bf.digital.pilotage.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class RoleRequest {

    private String nom;

    private String description;

    // Liste des identifiants des permissions
    private Set<Long> permissionIds;
}
