package cz.cvut.userservice.rest;

import cz.cvut.userservice.dto.AuthorizationResponse;
import cz.cvut.userservice.dto.LoginRequest;
import cz.cvut.userservice.dto.RegisterRequest;
import cz.cvut.userservice.dto.TokenPairDto;
import cz.cvut.userservice.dto.error.ApiErrorMultipleResponses;
import cz.cvut.userservice.dto.error.ApiErrorSingleResponse;
import cz.cvut.userservice.exception.JwtErrorException;
import cz.cvut.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthorizationResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Request received to login user: {}", request);
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Refresh access token")
    @ApiResponse(responseCode = "200", description = "Access token has been refreshed successfully.")
    @ApiResponse(responseCode = "403", description = "Something goes wrong while validating refresh token.", content = @Content(schema = @Schema(implementation = ApiErrorSingleResponse.class)))
    @PostMapping(value = "/refresh-token")
    public ResponseEntity<TokenPairDto> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("Request received to refresh access token");

        if (refreshToken == null || refreshToken.isEmpty())
            throw new JwtErrorException("Refresh token is not detected in auth header.");
        TokenPairDto tokenPair = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(tokenPair);
    }
}
