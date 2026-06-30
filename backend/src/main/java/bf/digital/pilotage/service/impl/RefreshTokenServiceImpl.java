package bf.digital.pilotage.service.impl;

import bf.digital.pilotage.entity.RefreshToken;
import bf.digital.pilotage.entity.Utilisateur;
import bf.digital.pilotage.exception.BadRequestException;
import bf.digital.pilotage.repository.RefreshTokenRepository;
import bf.digital.pilotage.service.token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Override
    @Transactional
    public RefreshToken creer(Utilisateur utilisateur) {
        // Révoquer les anciens tokens de cet utilisateur
        refreshTokenRepository.deleteByUtilisateur(utilisateur);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .utilisateur(utilisateur)
                .expiration(LocalDateTime.now().plusSeconds(refreshExpiration / 1000))
                .revoque(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken valider(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Refresh token invalide."));

        if (refreshToken.getRevoque()) {
            throw new BadRequestException("Refresh token révoqué.");
        }

        if (refreshToken.isExpire()) {
            refreshTokenRepository.delete(refreshToken);
            throw new BadRequestException("Refresh token expiré. Veuillez vous reconnecter.");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void revoquer(Utilisateur utilisateur) {
        refreshTokenRepository.deleteByUtilisateur(utilisateur);
    }
}
