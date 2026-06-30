package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.LoginRequest;
import bf.digital.pilotage.dto.request.RefreshTokenRequest;
import bf.digital.pilotage.dto.request.RegisterRequest;
import bf.digital.pilotage.dto.response.LoginResponse;
import bf.digital.pilotage.dto.response.RegisterResponse;
import bf.digital.pilotage.entity.RefreshToken;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.repository.RoleRepository;
import bf.digital.pilotage.repository.UtilisateurRepository;
import bf.digital.pilotage.security.jwt.JwtService;
import bf.digital.pilotage.service.auth.AuthService;
import bf.digital.pilotage.service.token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Un compte existe déjà avec cet email.");
        }

        Role roleMembre = roleRepository.findByNom("MEMBRE")
                .orElseThrow(() -> new BadRequestException("Le rôle MEMBRE n'existe pas en base."));

        Set<Role> roles = new HashSet<>();
        roles.add(roleMembre);

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .actif(true)
                .roles(roles)
                .build();

        Utilisateur saved = utilisateurRepository.save(utilisateur);

        return new RegisterResponse(saved.getId(), saved.getEmail(), "Compte créé avec succès.");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable."));

        String accessToken = jwtService.generateToken(utilisateur.getEmail());
        RefreshToken refreshToken = refreshTokenService.creer(utilisateur);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .type("Bearer")
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.valider(request.getRefreshToken());
        Utilisateur utilisateur = refreshToken.getUtilisateur();

        String nouveauAccessToken = jwtService.generateToken(utilisateur.getEmail());
        RefreshToken nouveauRefreshToken = refreshTokenService.creer(utilisateur);

        return LoginResponse.builder()
                .accessToken(nouveauAccessToken)
                .refreshToken(nouveauRefreshToken.getToken())
                .type("Bearer")
                .build();
    }
}
