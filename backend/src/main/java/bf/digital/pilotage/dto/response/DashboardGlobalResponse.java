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
public class DashboardGlobalResponse {
    private int totalPlateaux;
    private long totalTaches;
    private long totalTachesTerminees;
    private long totalTachesBloquees;
    private long totalTachesEnRetard;
    private double pourcentageAvancementGlobal;
    private List<DashboardPlateauResponse> plateaux;
}
