package cz.cvut.moviemate.userservice.exception;

import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;

public class UserBannedException extends MovieMateBaseException {

    public UserBannedException(String message) {
        super(message, "USER_BANNED_ERROR");
    }
}
