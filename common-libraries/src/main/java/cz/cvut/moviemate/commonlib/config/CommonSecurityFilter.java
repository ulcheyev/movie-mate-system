package cz.cvut.moviemate.commonlib.config;

import cz.cvut.moviemate.commonlib.dto.AppUserClaimsDetails;
import cz.cvut.moviemate.commonlib.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonSecurityFilter extends OncePerRequestFilter {

    private static boolean userDetailsNotNull(String username, String email, List<String> roles) {
        return username != null && email != null && roles != null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Constants.AUTH_HEADER);

        if (authHeader == null || !isBearerHeaderPresent(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        AppUserClaimsDetails appUserClaimsDetails = extractClaims(request);
        UsernamePasswordAuthenticationToken auth = getAuthentication(appUserClaimsDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    public AppUserClaimsDetails extractClaims(HttpServletRequest request) {
        String username = request.getHeader(Constants.USERNAME_HEADER);
        String email = request.getHeader(Constants.EMAIL_HEADER);
        String rolesAsString = request.getHeader(Constants.ROLES_HEADER);
        String id = request.getHeader(Constants.USER_ID_HEADER);
        String token = request.getHeader(Constants.AUTH_HEADER);
        // Remove the square brackets and split the string by commas
        List<String> roles = Arrays.stream(rolesAsString.replaceAll("[\\[\\]]", "")  // Remove brackets
                        .split(","))
                .map(String::trim)
                .toList();
        return new AppUserClaimsDetails(id, username, email, roles, token);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(AppUserClaimsDetails appUserClaimsDetails) {
        String username = appUserClaimsDetails.username();
        String email = appUserClaimsDetails.email();
        List<String> roles = appUserClaimsDetails.roles();
        if (userDetailsNotNull(username, email, roles)) {

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(new Principal(username,
                    appUserClaimsDetails.id()),
                    appUserClaimsDetails.token(),
                    authorities);
        } else {
            throw new RuntimeException("User details cannot have null fields.");
        }
    }


    private boolean isBearerHeaderPresent(String authHeader) {
        return authHeader.startsWith("Bearer ");
    }

    @AllArgsConstructor
    @Data
    public static class Principal {
        private String username;
        private String id;
    }

}
