package bf.digital.pilotage.service.token;

import bf.digital.pilotage.entity.RefreshToken;
import bf.digital.pilotage.entity.Utilisateur;

public interface RefreshTokenService {
    RefreshToken creer(Utilisateur utilisateur);
    RefreshToken valider(String token);
    void revoquer(Utilisateur utilisateur);
}
