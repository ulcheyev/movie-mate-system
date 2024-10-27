package cz.cvut.userservice.service;

import cz.cvut.userservice.dto.*;

public interface AuthService {

    AuthorizationResponse login(LoginRequest loginRequest);

    AuthorizationResponse register(RegisterRequest registerRequest);

    AppUserClaims validateToken(String token);

    TokenPairDto refreshToken(String refreshToken);
}
