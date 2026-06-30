package bf.digital.pilotage.service.auth;

import bf.digital.pilotage.dto.request.LoginRequest;
import bf.digital.pilotage.dto.request.RefreshTokenRequest;
import bf.digital.pilotage.dto.request.RegisterRequest;
import bf.digital.pilotage.dto.response.LoginResponse;
import bf.digital.pilotage.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(RefreshTokenRequest request);
}
