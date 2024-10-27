package cz.cvut.userservice.service;

import cz.cvut.userservice.dto.AuthorizationResponse;
import cz.cvut.userservice.dto.LoginRequest;
import cz.cvut.userservice.dto.RegisterRequest;

public interface AuthService {

    AuthorizationResponse login(LoginRequest loginRequest);

    AuthorizationResponse register(RegisterRequest registerRequest);
}
