package cz.cvut.moviemate.movieservice.config.filter;

import cz.cvut.moviemate.commonlib.config.CommonSecurityFilter;
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
public class SecurityFilter extends CommonSecurityFilter {
}
