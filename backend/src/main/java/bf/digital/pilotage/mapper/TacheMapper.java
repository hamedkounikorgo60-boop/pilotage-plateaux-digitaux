package bf.digital.pilotage.mapper;

import bf.digital.pilotage.dto.response.MembreResponse;
import bf.digital.pilotage.dto.response.TacheResponse;
import bf.digital.pilotage.entity.Tache;
import bf.digital.pilotage.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TacheMapper {

    public TacheResponse toResponse(Tache tache) {
        Utilisateur createur = tache.getCreateur();

        List<MembreResponse> assignes = tache.getAssignes().stream()
                .map(u -> MembreResponse.builder()
                        .id(u.getId())
                        .nom(u.getNom())
                        .prenom(u.getPrenom())
                        .email(u.getEmail())
                        .build())
                .toList();

        return TacheResponse.builder()
                .id(tache.getId())
                .titre(tache.getTitre())
                .description(tache.getDescription())
                .priorite(tache.getPriorite())
                .statut(tache.getStatut())
                .echeance(tache.getEcheance())
                .commentaireBlocage(tache.getCommentaireBlocage())
                .plateauId(tache.getPlateau().getId())
                .plateauNom(tache.getPlateau().getNom())
                .createurNomComplet(createur != null
                        ? createur.getPrenom() + " " + createur.getNom()
                        : null)
                .assignes(assignes)
                .dateCreation(tache.getDateCreation())
                .build();
    }
}
