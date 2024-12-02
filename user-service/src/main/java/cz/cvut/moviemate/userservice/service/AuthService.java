package cz.cvut.moviemate.userservice.service;

import cz.cvut.moviemate.commonlib.dto.AppUserClaimsDetails;
import cz.cvut.moviemate.userservice.dto.AuthorizationResponse;
import cz.cvut.moviemate.userservice.dto.LoginRequest;
import cz.cvut.moviemate.userservice.dto.RegisterRequest;
import cz.cvut.moviemate.userservice.dto.TokenPairDto;

public interface AuthService {

    AuthorizationResponse login(LoginRequest loginRequest);

    AuthorizationResponse register(RegisterRequest registerRequest);

    TokenPairDto refreshToken(String refreshToken);

    AppUserClaimsDetails validateToken(String token);
}
