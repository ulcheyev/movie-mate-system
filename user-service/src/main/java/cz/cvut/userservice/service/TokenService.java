package cz.cvut.userservice.service;

import cz.cvut.userservice.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String extractUsername(String token);

    String generateAccessToken(AppUser appUser);

    String generateRefreshToken(AppUser appUser);

    boolean validateToken(String token, UserDetails userDetails);
}
