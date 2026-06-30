package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.CreatePlateauRequest;
import bf.digital.pilotage.dto.request.UpdatePlateauRequest;
import bf.digital.pilotage.dto.response.PlateauResponse;
import bf.digital.pilotage.entity.Plateau;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.mapper.PlateauMapper;
import bf.digital.pilotage.repository.PlateauRepository;
import bf.digital.pilotage.repository.UtilisateurRepository;
import bf.digital.pilotage.service.plateau.PlateauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlateauServiceImpl implements PlateauService {

    private final PlateauRepository plateauRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PlateauMapper plateauMapper;

    @Override
    public List<PlateauResponse> getAllPlateaux() {
        return plateauRepository.findAll().stream()
                .map(plateauMapper::toResponse)
                .toList();
    }

    @Override
    public PlateauResponse getPlateauById(Long id) {
        return plateauMapper.toResponse(findPlateauOrThrow(id));
    }

    @Override
    public PlateauResponse createPlateau(CreatePlateauRequest request) {
        if (plateauRepository.existsByNom(request.getNom())) {
            throw new BadRequestException("Un plateau avec ce nom existe déjà.");
        }

        Plateau plateau = Plateau.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .build();

        if (request.getChefPlateauId() != null) {
            Utilisateur chef = findUtilisateurOrThrow(request.getChefPlateauId());
            plateau.setChefPlateau(chef);
        }

        return plateauMapper.toResponse(plateauRepository.save(plateau));
    }

    @Override
    public PlateauResponse updatePlateau(Long id, UpdatePlateauRequest request) {
        Plateau plateau = findPlateauOrThrow(id);
        plateau.setNom(request.getNom());
        plateau.setDescription(request.getDescription());
        return plateauMapper.toResponse(plateauRepository.save(plateau));
    }

    @Override
    public void deletePlateau(Long id) {
        Plateau plateau = findPlateauOrThrow(id);
        plateauRepository.delete(plateau);
    }

    @Override
    public PlateauResponse assignerChef(Long plateauId, Long userId) {
        Plateau plateau = findPlateauOrThrow(plateauId);
        Utilisateur chef = findUtilisateurOrThrow(userId);
        plateau.setChefPlateau(chef);
        return plateauMapper.toResponse(plateauRepository.save(plateau));
    }

    @Override
    public PlateauResponse ajouterMembre(Long plateauId, Long userId) {
        Plateau plateau = findPlateauOrThrow(plateauId);
        Utilisateur utilisateur = findUtilisateurOrThrow(userId);

        if (utilisateur.getPlateau() != null && !utilisateur.getPlateau().getId().equals(plateauId)) {
            throw new BadRequestException("Cet utilisateur appartient déjà à un autre plateau.");
        }

        utilisateur.setPlateau(plateau);
        utilisateurRepository.save(utilisateur);

        return plateauMapper.toResponse(findPlateauOrThrow(plateauId));
    }

    @Override
    public PlateauResponse retirerMembre(Long plateauId, Long userId) {
        Plateau plateau = findPlateauOrThrow(plateauId);
        Utilisateur utilisateur = findUtilisateurOrThrow(userId);

        if (utilisateur.getPlateau() == null || !utilisateur.getPlateau().getId().equals(plateauId)) {
            throw new BadRequestException("Cet utilisateur n'appartient pas à ce plateau.");
        }

        utilisateur.setPlateau(null);
        utilisateurRepository.save(utilisateur);

        return plateauMapper.toResponse(plateau);
    }

    private Plateau findPlateauOrThrow(Long id) {
        return plateauRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Plateau introuvable avec l'id : " + id));
    }

    private Utilisateur findUtilisateurOrThrow(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable avec l'id : " + id));
    }
}
