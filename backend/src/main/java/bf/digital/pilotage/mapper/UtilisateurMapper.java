package bf.digital.pilotage.mapper;

import bf.digital.pilotage.dto.response.UserResponse;
import bf.digital.pilotage.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UserResponse toResponse(Utilisateur utilisateur) {
        return UserResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .actif(utilisateur.getActif())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().getNom() : null)
                .dateCreation(utilisateur.getDateCreation())
                .build();
    }
}
