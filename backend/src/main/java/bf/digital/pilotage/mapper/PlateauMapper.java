package bf.digital.pilotage.mapper;

import bf.digital.pilotage.dto.response.MembreResponse;
import bf.digital.pilotage.dto.response.PlateauResponse;
import bf.digital.pilotage.entity.Plateau;
import bf.digital.pilotage.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlateauMapper {

    public PlateauResponse toResponse(Plateau plateau) {
        Utilisateur chef = plateau.getChefPlateau();

        List<MembreResponse> membres = plateau.getMembres().stream()
                .map(this::toMembreResponse)
                .toList();

        return PlateauResponse.builder()
                .id(plateau.getId())
                .nom(plateau.getNom())
                .description(plateau.getDescription())
                .chefPlateauId(chef != null ? chef.getId() : null)
                .chefPlateauNomComplet(chef != null ? chef.getPrenom() + " " + chef.getNom() : null)
                .membres(membres)
                .build();
    }

    public MembreResponse toMembreResponse(Utilisateur utilisateur) {
        return MembreResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .build();
    }
}
