package cz.cvut.userservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtil {

    public String getPrincipalUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails)
                return userDetails.getUsername();
            if (principal instanceof String username)
                return username;
        }
        throw new IllegalStateException("Unable to fetch the username from the authentication context");
    }
}
