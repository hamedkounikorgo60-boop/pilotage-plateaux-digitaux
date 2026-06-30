package bf.digital.pilotage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlateauResponse {
    private Long id;
    private String nom;
    private String description;
    private Long chefPlateauId;
    private String chefPlateauNomComplet;
    private List<MembreResponse> membres;
}
