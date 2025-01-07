package cz.cvut.moviemate.commonlib.utils;

import cz.cvut.moviemate.commonlib.config.CommonSecurityFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    private SecurityUtils() {}

    public static String getPrincipalUsername(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            }
            else if (principal instanceof CommonSecurityFilter.Principal principal1) {
                return principal1.getUsername();
            }
            else if (principal instanceof String username) {
                return username;
            }
        }
        throw new IllegalStateException("Unable to fetch the username from the authentication context");
    }

    public static String getPrincipalID(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
             if (principal instanceof CommonSecurityFilter.Principal principal1) {
                return principal1.getId();
            }
        }
        throw new IllegalStateException("Unable to fetch the username from the authentication context");
    }
}
