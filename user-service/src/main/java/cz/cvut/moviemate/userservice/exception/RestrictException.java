package cz.cvut.moviemate.userservice.exception;

import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;

public class RestrictException extends MovieMateBaseException {

    public RestrictException(String message) {
        super(message, "RESTRICT_EXCEPTION");
    }
}
