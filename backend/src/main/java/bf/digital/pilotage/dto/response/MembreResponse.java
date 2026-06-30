package bf.digital.pilotage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembreResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
