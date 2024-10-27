package cz.cvut.userservice.exception.handler;

import cz.cvut.userservice.dto.error.ApiErrorSingleResponse;
import cz.cvut.userservice.exception.JwtErrorException;
import cz.cvut.userservice.exception.UserBannedException;
import cz.cvut.userservice.util.ApiErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
@Order(1)
public class AuthExceptionHandler {
    private final ApiErrorFactory apiErrorFactory;

    @ExceptionHandler(UserBannedException.class)
    public ResponseEntity<ApiErrorSingleResponse> handleLockedException(UserBannedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.LOCKED;

        ApiErrorSingleResponse errorResponse = apiErrorFactory.createApiErrorResponse(
                status,
                ex.getErrorType(),
                "Banned user tried to access the app.",
                ex.getMessage(),
                request,
                ex.getCause(),
                new String[]{"Contact support to unlock your account."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(JwtErrorException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleUnauthorizedException(JwtErrorException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorSingleResponse errorResponse = apiErrorFactory.createApiErrorResponse(
                status,
                ex.getErrorType(),
                ex.getMessage(),
                "Malformed token has been received.",
                request,
                ex.getCause(),
                new String[]{"Please contact support for further assistance."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorSingleResponse> handleBadCredentials(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiErrorSingleResponse errorResponse = apiErrorFactory.createApiErrorResponse(
                status,
                "BAD_CREDENTIALS",
                "Provided incorrect credentials.",
                ex.getMessage(),
                request,
                ex.getCause(),
                new String[]{"Verify your credentials and try again.", "Contact support if the problem persists."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
