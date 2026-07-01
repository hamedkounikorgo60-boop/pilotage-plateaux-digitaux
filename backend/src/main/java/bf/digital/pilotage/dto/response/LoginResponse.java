package bf.digital.pilotage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String type;
    private Long userId;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private Long roleId;
    private Set<String> permissions;
}
