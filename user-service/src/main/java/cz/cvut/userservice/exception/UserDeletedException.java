package cz.cvut.userservice.exception;

public class UserDeletedException extends MovieMateBaseException{

    public UserDeletedException(String message) {
        super(message, "USER_DELETED_EXCEPTION");
    }
}
