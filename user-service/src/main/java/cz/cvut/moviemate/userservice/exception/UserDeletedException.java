package cz.cvut.moviemate.userservice.exception;

import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;

public class UserDeletedException extends MovieMateBaseException {

    public UserDeletedException(String message) {
        super(message, "USER_DELETED_EXCEPTION");
    }
}
