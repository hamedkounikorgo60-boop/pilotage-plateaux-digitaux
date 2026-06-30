package bf.digital.pilotage.service.dashboard;

import bf.digital.pilotage.dto.response.DashboardGlobalResponse;
import bf.digital.pilotage.dto.response.DashboardPlateauResponse;

public interface DashboardService {
    DashboardGlobalResponse getDashboardGlobal();
    DashboardPlateauResponse getDashboardPlateau(Long plateauId);
}
