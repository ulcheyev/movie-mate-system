package cz.cvut.moviemate.commonlib.utils;

public class Constants {

    private Constants() {}

    public final static String AUTH_HEADER = "Authorization";
    public static final String USERNAME_HEADER = "X-User-Username";
    public static final String EMAIL_HEADER = "X-User-Email";
    public static final String ROLES_HEADER = "X-User-Roles";
    public static final String USER_ID_HEADER = "X-User-Id";

    public static final String SERVICE_USER_SIGN_UP_TOPIC = "sign-up";
    public static final String SERVICE_WATCHLIST_EDIT_TOPIC = "watchlist-edit";
    public static final String SERVICE_ACTIVITY_RATING_TOPIC = "activity-rating";

}
