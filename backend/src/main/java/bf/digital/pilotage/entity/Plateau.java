package bf.digital.pilotage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plateaux")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plateau extends BaseEntity {

    @Column(nullable = false, unique = true, length = 150)
    private String nom;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_plateau_id")
    private Utilisateur chefPlateau;

    @OneToMany(mappedBy = "plateau", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Utilisateur> membres = new HashSet<>();
}
