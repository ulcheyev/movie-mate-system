package cz.cvut.moviemate.commonlib.utils;

import cz.cvut.moviemate.commonlib.dto.AppUserClaimsDetails;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityFilterUtil {

    private static boolean userDetailsNotNull(String username, String email, List<String> roles) {
        return username != null && email != null && roles != null;
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

            List<? extends GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(username, appUserClaimsDetails.token(), authorities);
        } else {
            throw new RuntimeException("User details cannot have null fields.");
        }
    }
}
