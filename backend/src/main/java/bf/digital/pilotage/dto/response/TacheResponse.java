package bf.digital.pilotage.dto.response;

import bf.digital.pilotage.enums.PrioriteTache;
import bf.digital.pilotage.enums.StatutTache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacheResponse {
    private Long id;
    private String titre;
    private String description;
    private PrioriteTache priorite;
    private StatutTache statut;
    private LocalDate echeance;
    private String commentaireBlocage;
    private Long plateauId;
    private String plateauNom;
    private String createurNomComplet;
    private List<MembreResponse> assignes;
    private LocalDateTime dateCreation;
}
