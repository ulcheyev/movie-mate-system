package cz.cvut.watchlistservice.config;

import cz.cvut.moviemate.commonlib.dto.AppUserClaimsDetails;
import cz.cvut.moviemate.commonlib.utils.SecurityFilterUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final SecurityFilterUtil securityFilterUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AppUserClaimsDetails appUserClaimsDetails = securityFilterUtil.extractClaims(request);
        UsernamePasswordAuthenticationToken auth = securityFilterUtil.getAuthentication(appUserClaimsDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}