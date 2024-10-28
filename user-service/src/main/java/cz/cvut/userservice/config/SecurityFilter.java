package cz.cvut.userservice.config;

import cz.cvut.userservice.exception.JwtErrorException;
import cz.cvut.userservice.exception.UserBannedException;
import cz.cvut.userservice.service.InternalAppUserService;
import cz.cvut.userservice.service.TokenService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j(topic = "USER_SERVICE_SECURITY_FILTER")
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";

    private final TokenService tokenService;
    private final InternalAppUserService internalAppUserService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (!isBearerHeaderPresent(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        try {
            processToken(token, request);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handleJwtException(e, request, response);
        } catch (UserBannedException e) {
            handleUserBannedException(e, request, response);
        }
    }

    private void processToken(String token, HttpServletRequest request) {
        String username = tokenService.extractUsername(token);

        if (isNotAuthenticated(username)) {
            UserDetails userDetails = internalAppUserService.loadUserByUsername(username);
            checkAccountLocking(userDetails);

            if (tokenService.validateToken(token, userDetails))
                setAuthContext(userDetails, request);
        }
    }

    private void setAuthContext(UserDetails userDetails, HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }

    private boolean isNotAuthenticated(String username) {
        return StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private boolean isBearerHeaderPresent(String authHeader) {
        return !StringUtils.isEmpty(authHeader) || StringUtils.startsWith(authHeader, BEARER_PREFIX);
    }

    private void checkAccountLocking(UserDetails userDetails) {
        String username = userDetails.getUsername();

        if (!userDetails.isAccountNonLocked()) {
            log.warn("User {} with banned account tried to access the app.", username);
            throw new UserBannedException(String.format("Account with username %s is banned.", username));
        }
    }

    private void handleJwtException(JwtException e, HttpServletRequest request, HttpServletResponse response) {
        handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new JwtErrorException("Error while processing token: " + e.getMessage())
        );
    }

    private void handleUserBannedException(UserBannedException e, HttpServletRequest request, HttpServletResponse response) {
        handlerExceptionResolver.resolveException(
                request,
                response,
                null,
                new UserBannedException(e.getMessage())
        );
    }
}
