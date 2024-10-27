package cz.cvut.userservice.exception;

public class UserBannedException extends MovieMateBaseException {

    public UserBannedException(String message) {
        super(message, "USER_BANNED_ERROR");
    }
}
