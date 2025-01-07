package cz.cvut.moviemate.commonlib.dto.events;

public record UserSignUpEvent(Long userId, String username, String email) {}
