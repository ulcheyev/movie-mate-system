package cz.cvut.moviemate.commonlib.error.handler;

import cz.cvut.moviemate.commonlib.error.ApiErrorSingleResponse;
import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;
import cz.cvut.moviemate.commonlib.exception.NotFoundException;
import cz.cvut.moviemate.commonlib.utils.ApiErrorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieMateBaseException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleMovieMateBaseException(MovieMateBaseException ex, WebRequest request) {
        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                HttpStatus.I_AM_A_TEAPOT,
                "GENERIC_ERROR",
                ex.getMessage(),
                "Something went wrong by manki.",
                request,
                ex.getCause(),
                new String[]{"Check application logs for more details."}
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleResourceNotFoundException(NotFoundException ex, WebRequest request) {
        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                "Resource with specified identifier was not found.",
                request,
                ex.getCause(),
                new String[]{"Ensure the resource ID is correct."}
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleAuthenticationException(
            AuthenticationException ex, WebRequest webRequest) {
        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "UNAUTHORIZED",
                ex.getMessage(),
                "Access is denied due to invalid credentials.",
                webRequest,
                ex.getCause(),
                new String[]{"Ensure the correct credentials are used."}
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleGlobalException(Exception ex, WebRequest webRequest) {
        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                ex.getMessage(),
                "Something went wrong...",
                webRequest,
                ex.getCause(),
                new String[]{"Contact support if the issue persists."}
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}