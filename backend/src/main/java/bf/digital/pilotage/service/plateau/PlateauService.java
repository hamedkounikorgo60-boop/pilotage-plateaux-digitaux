package bf.digital.pilotage.service.plateau;

import bf.digital.pilotage.dto.request.CreatePlateauRequest;
import bf.digital.pilotage.dto.request.UpdatePlateauRequest;
import bf.digital.pilotage.dto.response.PlateauResponse;

import java.util.List;

public interface PlateauService {
    List<PlateauResponse> getAllPlateaux();
    PlateauResponse getPlateauById(Long id);
    PlateauResponse createPlateau(CreatePlateauRequest request);
    PlateauResponse updatePlateau(Long id, UpdatePlateauRequest request);
    void deletePlateau(Long id);
    PlateauResponse assignerChef(Long plateauId, Long userId);
    PlateauResponse ajouterMembre(Long plateauId, Long userId);
    PlateauResponse retirerMembre(Long plateauId, Long userId);
}
