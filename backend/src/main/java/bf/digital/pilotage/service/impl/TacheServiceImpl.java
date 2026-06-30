package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.AssignerTacheRequest;
import bf.digital.pilotage.dto.request.CreateTacheRequest;
import bf.digital.pilotage.dto.request.UpdateStatutRequest;
import bf.digital.pilotage.dto.request.UpdateTacheRequest;
import bf.digital.pilotage.dto.response.TacheResponse;
import bf.digital.pilotage.entity.Plateau;
import bf.digital.pilotage.entity.Tache;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.enums.PrioriteTache;
import bf.digital.pilotage.enums.StatutTache;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.mapper.TacheMapper;
import bf.digital.pilotage.repository.PlateauRepository;
import bf.digital.pilotage.repository.TacheRepository;
import bf.digital.pilotage.repository.UtilisateurRepository;
import bf.digital.pilotage.service.tache.TacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;
    private final PlateauRepository plateauRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final TacheMapper tacheMapper;

    @Override
    public List<TacheResponse> getAllTaches() {
        return tacheRepository.findAll().stream()
                .map(tacheMapper::toResponse)
                .toList();
    }

    @Override
    public List<TacheResponse> getTachesByPlateau(Long plateauId) {
        findPlateauOrThrow(plateauId);
        return tacheRepository.findByPlateauId(plateauId).stream()
                .map(tacheMapper::toResponse)
                .toList();
    }

    @Override
    public List<TacheResponse> getMesTaches(String email) {
        Utilisateur utilisateur = findUtilisateurByEmailOrThrow(email);
        return tacheRepository.findByAssignesId(utilisateur.getId()).stream()
                .map(tacheMapper::toResponse)
                .toList();
    }

    @Override
    public TacheResponse getTacheById(Long id) {
        return tacheMapper.toResponse(findTacheOrThrow(id));
    }

    @Override
    public TacheResponse createTache(CreateTacheRequest request, String emailCreateur) {
        Plateau plateau = findPlateauOrThrow(request.getPlateauId());
        Utilisateur createur = findUtilisateurByEmailOrThrow(emailCreateur);

        Set<Utilisateur> assignes = new HashSet<>();
        if (request.getAssignesIds() != null) {
            for (Long userId : request.getAssignesIds()) {
                assignes.add(findUtilisateurOrThrow(userId));
            }
        }

        Tache tache = Tache.builder()
                .titre(request.getTitre())
                .description(request.getDescription())
                .priorite(request.getPriorite() != null ? request.getPriorite() : PrioriteTache.MOYENNE)
                .statut(StatutTache.A_FAIRE)
                .echeance(request.getEcheance())
                .plateau(plateau)
                .createur(createur)
                .assignes(assignes)
                .build();

        return tacheMapper.toResponse(tacheRepository.save(tache));
    }

    @Override
    public TacheResponse updateTache(Long id, UpdateTacheRequest request) {
        Tache tache = findTacheOrThrow(id);
        tache.setTitre(request.getTitre());
        tache.setDescription(request.getDescription());
        tache.setEcheance(request.getEcheance());
        if (request.getPriorite() != null) {
            tache.setPriorite(request.getPriorite());
        }
        return tacheMapper.toResponse(tacheRepository.save(tache));
    }

    @Override
    public void deleteTache(Long id) {
        tacheRepository.delete(findTacheOrThrow(id));
    }

    @Override
    public TacheResponse updateStatut(Long id, UpdateStatutRequest request) {
        Tache tache = findTacheOrThrow(id);
        tache.setStatut(request.getStatut());

        if (request.getStatut() == StatutTache.BLOQUE) {
            if (request.getCommentaireBlocage() == null || request.getCommentaireBlocage().isBlank()) {
                throw new BadRequestException("Un commentaire est obligatoire pour signaler un blocage.");
            }
            tache.setCommentaireBlocage(request.getCommentaireBlocage());
        } else {
            tache.setCommentaireBlocage(null);
        }

        return tacheMapper.toResponse(tacheRepository.save(tache));
    }

    @Override
    public TacheResponse assignerMembres(Long id, AssignerTacheRequest request) {
        Tache tache = findTacheOrThrow(id);

        Set<Utilisateur> assignes = new HashSet<>();
        for (Long userId : request.getAssignesIds()) {
            assignes.add(findUtilisateurOrThrow(userId));
        }

        tache.setAssignes(assignes);
        return tacheMapper.toResponse(tacheRepository.save(tache));
    }

    private Tache findTacheOrThrow(Long id) {
        return tacheRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Tâche introuvable avec l'id : " + id));
    }

    private Plateau findPlateauOrThrow(Long id) {
        return plateauRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Plateau introuvable avec l'id : " + id));
    }

    private Utilisateur findUtilisateurOrThrow(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable avec l'id : " + id));
    }

    private Utilisateur findUtilisateurByEmailOrThrow(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable."));
    }
}
