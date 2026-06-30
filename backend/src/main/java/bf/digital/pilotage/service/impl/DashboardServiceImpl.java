package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.response.DashboardGlobalResponse;
import bf.digital.pilotage.dto.response.DashboardPlateauResponse;
import bf.digital.pilotage.entity.Plateau;
import bf.digital.pilotage.entity.Tache;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.enums.StatutTache;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.repository.PlateauRepository;
import bf.digital.pilotage.repository.TacheRepository;
import bf.digital.pilotage.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PlateauRepository plateauRepository;
    private final TacheRepository tacheRepository;

    @Override
    public DashboardGlobalResponse getDashboardGlobal() {
        List<Plateau> plateaux = plateauRepository.findAll();

        List<DashboardPlateauResponse> stats = plateaux.stream()
                .map(p -> buildPlateauStats(p))
                .toList();

        long totalTaches = stats.stream().mapToLong(DashboardPlateauResponse::getTotalTaches).sum();
        long totalTerminees = stats.stream().mapToLong(DashboardPlateauResponse::getTachesTerminees).sum();
        long totalBloquees = stats.stream().mapToLong(DashboardPlateauResponse::getTachesBloquees).sum();
        long totalEnRetard = stats.stream().mapToLong(DashboardPlateauResponse::getTachesEnRetard).sum();

        double avancementGlobal = totalTaches > 0
                ? Math.round((double) totalTerminees / totalTaches * 100.0 * 10) / 10.0
                : 0.0;

        return DashboardGlobalResponse.builder()
                .totalPlateaux(plateaux.size())
                .totalTaches(totalTaches)
                .totalTachesTerminees(totalTerminees)
                .totalTachesBloquees(totalBloquees)
                .totalTachesEnRetard(totalEnRetard)
                .pourcentageAvancementGlobal(avancementGlobal)
                .plateaux(stats)
                .build();
    }

    @Override
    public DashboardPlateauResponse getDashboardPlateau(Long plateauId) {
        Plateau plateau = plateauRepository.findById(plateauId)
                .orElseThrow(() -> new BadRequestException("Plateau introuvable avec l'id : " + plateauId));
        return buildPlateauStats(plateau);
    }

    private DashboardPlateauResponse buildPlateauStats(Plateau plateau) {
        List<Tache> taches = tacheRepository.findByPlateauId(plateau.getId());
        LocalDate aujourd_hui = LocalDate.now();

        long total = taches.size();
        long terminees = taches.stream()
                .filter(t -> t.getStatut() == StatutTache.TERMINE)
                .count();
        long enCours = taches.stream()
                .filter(t -> t.getStatut() == StatutTache.EN_COURS)
                .count();
        long bloquees = taches.stream()
                .filter(t -> t.getStatut() == StatutTache.BLOQUE)
                .count();
        long enRetard = taches.stream()
                .filter(t -> t.getStatut() != StatutTache.TERMINE
                        && t.getEcheance() != null
                        && t.getEcheance().isBefore(aujourd_hui))
                .count();

        double avancement = total > 0
                ? Math.round((double) terminees / total * 100.0 * 10) / 10.0
                : 0.0;

        Utilisateur chef = plateau.getChefPlateau();
        String chefNom = chef != null ? chef.getPrenom() + " " + chef.getNom() : "Non assigné";

        return DashboardPlateauResponse.builder()
                .plateauId(plateau.getId())
                .plateauNom(plateau.getNom())
                .chefPlateau(chefNom)
                .nombreMembres(plateau.getMembres().size())
                .totalTaches(total)
                .tachesTerminees(terminees)
                .tachesEnCours(enCours)
                .tachesBloquees(bloquees)
                .tachesEnRetard(enRetard)
                .pourcentageAvancement(avancement)
                .build();
    }
}
