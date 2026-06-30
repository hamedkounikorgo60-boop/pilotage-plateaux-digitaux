package bf.digital.pilotage.service.tache;

import bf.digital.pilotage.dto.request.AssignerTacheRequest;
import bf.digital.pilotage.dto.request.CreateTacheRequest;
import bf.digital.pilotage.dto.request.UpdateStatutRequest;
import bf.digital.pilotage.dto.request.UpdateTacheRequest;
import bf.digital.pilotage.dto.response.TacheResponse;

import java.util.List;

public interface TacheService {
    List<TacheResponse> getAllTaches();
    List<TacheResponse> getTachesByPlateau(Long plateauId);
    List<TacheResponse> getMesTaches(String email);
    TacheResponse getTacheById(Long id);
    TacheResponse createTache(CreateTacheRequest request, String emailCreateur);
    TacheResponse updateTache(Long id, UpdateTacheRequest request);
    void deleteTache(Long id);
    TacheResponse updateStatut(Long id, UpdateStatutRequest request);
    TacheResponse assignerMembres(Long id, AssignerTacheRequest request);
}
