package bf.digital.pilotage.controller;

import bf.digital.pilotage.dto.response.DashboardGlobalResponse;
import bf.digital.pilotage.dto.response.DashboardPlateauResponse;
import bf.digital.pilotage.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Tableau de bord", description = "Statistiques et rapports d'avancement")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ResponseEntity<DashboardGlobalResponse> getDashboardGlobal() {
        return ResponseEntity.ok(dashboardService.getDashboardGlobal());
    }

    @GetMapping("/plateau/{id}")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ResponseEntity<DashboardPlateauResponse> getDashboardPlateau(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getDashboardPlateau(id));
    }
}
