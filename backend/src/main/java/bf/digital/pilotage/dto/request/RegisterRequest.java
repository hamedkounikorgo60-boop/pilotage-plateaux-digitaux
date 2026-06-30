package bf.digital.pilotage.dto.request;

import lombok.Data;


@Data
public class RegisterRequest {

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private String motDePasse;

}