package cz.cvut.userservice.exception;

public class NotFoundException extends MovieMateBaseException{

    public NotFoundException(String message) {
        super(message, "NOT_FOUND_ERROR");
    }
}
