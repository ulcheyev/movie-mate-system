package cz.cvut.moviemate.userservice.exception;

import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;

public class JwtErrorException extends MovieMateBaseException {

    public JwtErrorException(String message) {
        super(message, "JWT_ERROR");
    }
}
