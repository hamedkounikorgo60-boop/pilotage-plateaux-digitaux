package bf.digital.pilotage.security.service;

import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Utilisateur introuvable"));

        Set<GrantedAuthority> authorities = new HashSet<>();

        if (utilisateur.getRole() != null) {
            // Rôle → hasRole("ADMIN")
            authorities.add(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().getNom()));

            // Permissions → hasAuthority("USER_CREATE")
            utilisateur.getRole().getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getNom()))
            );
        }

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .disabled(!Boolean.TRUE.equals(utilisateur.getActif()))
                .authorities(authorities)
                .build();
    }
}
