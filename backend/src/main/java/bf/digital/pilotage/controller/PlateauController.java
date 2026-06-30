package bf.digital.pilotage.controller;

import bf.digital.pilotage.dto.request.CreatePlateauRequest;
import bf.digital.pilotage.dto.request.UpdatePlateauRequest;
import bf.digital.pilotage.dto.response.PlateauResponse;
import bf.digital.pilotage.service.plateau.PlateauService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plateaux")
@RequiredArgsConstructor
@Tag(name = "Plateaux", description = "Gestion des plateaux et des équipes")
public class PlateauController {

    private final PlateauService plateauService;

    @GetMapping
    @PreAuthorize("hasAuthority('PLATEAU_READ')")
    public ResponseEntity<List<PlateauResponse>> getAllPlateaux() {
        return ResponseEntity.ok(plateauService.getAllPlateaux());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLATEAU_READ')")
    public ResponseEntity<PlateauResponse> getPlateauById(@PathVariable Long id) {
        return ResponseEntity.ok(plateauService.getPlateauById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PLATEAU_CREATE')")
    public ResponseEntity<PlateauResponse> createPlateau(@Valid @RequestBody CreatePlateauRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plateauService.createPlateau(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PLATEAU_UPDATE')")
    public ResponseEntity<PlateauResponse> updatePlateau(@PathVariable Long id,
                                                            @Valid @RequestBody UpdatePlateauRequest request) {
        return ResponseEntity.ok(plateauService.updatePlateau(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PLATEAU_DELETE')")
    public ResponseEntity<Void> deletePlateau(@PathVariable Long id) {
        plateauService.deletePlateau(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/chef/{userId}")
    @PreAuthorize("hasAuthority('PLATEAU_UPDATE')")
    public ResponseEntity<PlateauResponse> assignerChef(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(plateauService.assignerChef(id, userId));
    }

    @PostMapping("/{id}/membres/{userId}")
    @PreAuthorize("hasAuthority('PLATEAU_UPDATE')")
    public ResponseEntity<PlateauResponse> ajouterMembre(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(plateauService.ajouterMembre(id, userId));
    }

    @DeleteMapping("/{id}/membres/{userId}")
    @PreAuthorize("hasAuthority('PLATEAU_UPDATE')")
    public ResponseEntity<PlateauResponse> retirerMembre(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(plateauService.retirerMembre(id, userId));
    }
}
