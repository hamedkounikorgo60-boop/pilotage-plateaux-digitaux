package bf.digital.pilotage.entity;

import bf.digital.pilotage.enums.PrioriteTache;
import bf.digital.pilotage.enums.StatutTache;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "taches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tache extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PrioriteTache priorite = PrioriteTache.MOYENNE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutTache statut = StatutTache.A_FAIRE;

    @Column
    private LocalDate echeance;

    @Column(length = 500)
    private String commentaireBlocage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plateau_id", nullable = false)
    private Plateau plateau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id")
    private Utilisateur createur;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tache_assignees",
            joinColumns = @JoinColumn(name = "tache_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    @Builder.Default
    private Set<Utilisateur> assignes = new HashSet<>();
}
