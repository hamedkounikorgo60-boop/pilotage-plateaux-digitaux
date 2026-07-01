package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.dto.request.ChangePasswordRequest;
import bf.digital.pilotage.dto.request.CreateUserRequest;
import bf.digital.pilotage.dto.request.UpdateProfileRequest;
import bf.digital.pilotage.dto.request.UpdateUserRequest;
import bf.digital.pilotage.dto.response.UserResponse;
import bf.digital.pilotage.entity.Role;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.mapper.UtilisateurMapper;
import bf.digital.pilotage.repository.RoleRepository;
import bf.digital.pilotage.repository.UtilisateurRepository;
import bf.digital.pilotage.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper mapper;

    @Override
    public List<UserResponse> getAllUsers() {
        return utilisateurRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return mapper.toResponse(findUserOrThrow(id));
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Un compte existe déjà avec cet email.");
        }

        Role role = roleRepository.findByNom(request.getRole())
                .orElseThrow(() -> new BadRequestException("Rôle inconnu : " + request.getRole()));

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .actif(true)
                .role(role)
                .build();

        return mapper.toResponse(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        Utilisateur utilisateur = findUserOrThrow(id);
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setTelephone(request.getTelephone());
        return mapper.toResponse(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void deleteUser(Long id) {
        utilisateurRepository.delete(findUserOrThrow(id));
    }

    @Override
    public void desactiverUser(Long id) {
        Utilisateur utilisateur = findUserOrThrow(id);
        utilisateur.setActif(false);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public UserResponse getProfile(String email) {
        return mapper.toResponse(findUserByEmailOrThrow(email));
    }

    @Override
    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        Utilisateur utilisateur = findUserByEmailOrThrow(email);
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setTelephone(request.getTelephone());
        return mapper.toResponse(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        Utilisateur utilisateur = findUserByEmailOrThrow(email);

        if (!passwordEncoder.matches(request.getAncienMotDePasse(), utilisateur.getMotDePasse())) {
            throw new BadRequestException("Ancien mot de passe incorrect.");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);
    }

    private Utilisateur findUserOrThrow(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable avec l'id : " + id));
    }

    private Utilisateur findUserByEmailOrThrow(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Utilisateur introuvable."));
    }
}
