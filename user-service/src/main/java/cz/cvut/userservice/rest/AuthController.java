package cz.cvut.userservice.rest;

import cz.cvut.userservice.dto.*;
import cz.cvut.userservice.dto.error.ApiErrorMultipleResponses;
import cz.cvut.userservice.dto.error.ApiErrorSingleResponse;
import cz.cvut.userservice.exception.JwtErrorException;
import cz.cvut.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static cz.cvut.userservice.config.SecurityFilter.AUTH_HEADER;
import static cz.cvut.userservice.config.SecurityFilter.BEARER_PREFIX;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTH_CONTROLLER")
@RequiredArgsConstructor
@Tag(name = "Authentication/Authorization", description = "Authentication and authorization management API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "200", description = "User successfully registered.", content = @Content(schema = @Schema(implementation = AuthorizationResponse.class)))
    @ApiResponse(responseCode = "401", description = "Internal app error while generating token.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @ApiResponse(responseCode = "409", description = "Username/email duplicate detection.", content = @Content(schema = @Schema(implementation = ApiErrorMultipleResponses.class)))
    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorizationResponse> register(@RequestBody @Valid RegisterRequest request) {
        log.info("Request received to register user: {}", request);
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = "User successfully logged in.", content = @Content(schema = @Schema(implementation = AuthorizationResponse.class)))
    @ApiResponse(responseCode = "404", description = "User with specified username/email was not found.", content = @Content(schema = @Schema(implementation = ApiErrorMultipleResponses.class)))
    @ApiResponse(responseCode = "417", description = "Invalid credentials.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @ApiResponse(responseCode = "423", description = "User account is banned.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorizationResponse> signIn(@RequestBody @Valid LoginRequest request) {
        log.info("Request received to login user: {}", request);
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Refresh token")
    @ApiResponse(responseCode = "200", description = "Access token has been refreshed successfully.")
    @ApiResponse(responseCode = "403", description = "Something goes wrong while validating refresh token.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @PostMapping(value = "/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest req, HttpServletResponse res) {
        log.info("Request received to refresh access token");

        final String authHeader = req.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX))
            throw new JwtErrorException("Refresh token is not detected in auth header.");

        final String refreshToken = authHeader.substring(BEARER_PREFIX.length());
        authService.refreshToken(refreshToken);

        return ResponseEntity.ok("Refreshed successfully.");
    }

    @Operation(summary = "Validate token")
    @ApiResponse(responseCode = "200", description = "Access token is valid.", content = @Content(schema = @Schema(implementation = AppUserClaims.class)))
    @ApiResponse(responseCode = "403", description = "Error occurred while validating token.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid access token and refresh token is not specified.")
    @PostMapping(value = "/validate-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUserClaims> validateToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String refreshToken
    ) {
        log.info("Request received to validate access token.");

        String token = authHeader.replace("Bearer ", "");
        AppUserClaims details;
        try {
            details = authService.validateToken(token);
        } catch (JwtErrorException ex) {
            if (refreshToken != null) { // Token is invalid or expired, try to refresh
                TokenPairDto newToken = authService.refreshToken(refreshToken);
                details = authService.validateToken(newToken.accessToken());
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok(details);
    }
}
