package bf.digital.pilotage.controller;

import bf.digital.pilotage.dto.request.AssignerTacheRequest;
import bf.digital.pilotage.dto.request.CreateTacheRequest;
import bf.digital.pilotage.dto.request.UpdateStatutRequest;
import bf.digital.pilotage.dto.request.UpdateTacheRequest;
import bf.digital.pilotage.dto.response.TacheResponse;
import bf.digital.pilotage.service.tache.TacheService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
@Tag(name = "Tâches", description = "Gestion des tâches")
public class TacheController {

    private final TacheService tacheService;

    @GetMapping
    @PreAuthorize("hasAuthority('TASK_READ')")
    public ResponseEntity<List<TacheResponse>> getAllTaches() {
        return ResponseEntity.ok(tacheService.getAllTaches());
    }

    @GetMapping("/plateau/{plateauId}")
    @PreAuthorize("hasAuthority('TASK_READ')")
    public ResponseEntity<List<TacheResponse>> getTachesByPlateau(@PathVariable Long plateauId) {
        return ResponseEntity.ok(tacheService.getTachesByPlateau(plateauId));
    }

    @GetMapping("/mes-taches")
    public ResponseEntity<List<TacheResponse>> getMesTaches(Authentication authentication) {
        return ResponseEntity.ok(tacheService.getMesTaches(authentication.getName()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_READ')")
    public ResponseEntity<TacheResponse> getTacheById(@PathVariable Long id) {
        return ResponseEntity.ok(tacheService.getTacheById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TASK_CREATE')")
    public ResponseEntity<TacheResponse> createTache(@Valid @RequestBody CreateTacheRequest request,
                                                       Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tacheService.createTache(request, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_UPDATE')")
    public ResponseEntity<TacheResponse> updateTache(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateTacheRequest request) {
        return ResponseEntity.ok(tacheService.updateTache(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_DELETE')")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAuthority('TASK_UPDATE')")
    public ResponseEntity<TacheResponse> updateStatut(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateStatutRequest request) {
        return ResponseEntity.ok(tacheService.updateStatut(id, request));
    }

    @PutMapping("/{id}/assigner")
    @PreAuthorize("hasAuthority('TASK_UPDATE')")
    public ResponseEntity<TacheResponse> assignerMembres(@PathVariable Long id,
                                                           @Valid @RequestBody AssignerTacheRequest request) {
        return ResponseEntity.ok(tacheService.assignerMembres(id, request));
    }
}
