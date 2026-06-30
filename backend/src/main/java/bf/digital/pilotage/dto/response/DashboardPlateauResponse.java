package bf.digital.pilotage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPlateauResponse {
    private Long plateauId;
    private String plateauNom;
    private String chefPlateau;
    private int nombreMembres;
    private long totalTaches;
    private long tachesTerminees;
    private long tachesEnCours;
    private long tachesBloquees;
    private long tachesEnRetard;
    private double pourcentageAvancement;
}
